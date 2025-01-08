package fr.eni.projetEnchere.bll.article;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.eni.projetEnchere.bo.Article;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Service
public class AuctionImageService {
	
	private final String imageDirectory; // the root folder of all auction storage
	private final String defaultImgName = "image_0.jpg";
	
	Logger logger = LoggerFactory.getLogger(AuctionImageService.class);
	
	private ArticleService articleService;

	// Constructor injection of the image directory global path into local var
	// could be "${image.directory:${user.dir}/images}" if we want a default value but I'd rather it crashes
    public AuctionImageService(@Value("${image.directory}") String imageDirectory, 
    		ArticleService articleService) {
        this.imageDirectory = imageDirectory;
        this.articleService = articleService;
    }
    
	
	// runs after the sql scripts have been initialized
    // DEV ONLY
	@PostConstruct
	public void startUpSequence() {
		logger.debug("Startup: Images: Launching StartUp Sequence");
		Path path = Paths.get(imageDirectory); // make the root
		// ABSOLUTELY DO NOT DO AN IF EXISTS DELETE (in prod at least)
        this.ifNotExistsCreate(path);
        
        List<Article> articlesFound = articleService.getAll();
        for (Article art : articlesFound) {
			if (art.getImgFileName() == null) {continue;}
			this.makeArticleFolders(getArticlePath(art), art.getImgFileName());
			
			art = articleService.getById(art.getIdArticle()); // doesn't work without
			art.setImgFileName(this.defaultImgName);
			articleService.update(art);
		}
	}
	
	// directory is the server/images/auction id/images path
	private void makeArticleFolders(String auctionImageDir, String imgFileName) {
		Path path = Paths.get(auctionImageDir);
        this.ifExistsDelete(path);
        this.ifNotExistsCreate(path);
        
        this.copyImageToFolder(imgFileName, auctionImageDir);
	}
	
	// DEV ONLY
	private void copyImageToFolder(String imgFileName, String destinationDir) {
        Path sourcePath = Paths.get("src/main/resources/static/test_images/"+imgFileName);
        Path destinationFolder = Paths.get(destinationDir);
        Path destinationFile = destinationFolder.resolve(this.defaultImgName); // full path to new img

        try {
            // Copy the image file to the new folder
            Files.copy(sourcePath, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Startup: Images: Image "+imgFileName+" copied successfully to: " + destinationFile);

        } catch (IOException e) {
            logger.error("Startup: Images: ERROR: Failed to copy the image: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	
    private void ifNotExistsCreate(Path path) {
    	if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.debug("> Images: created directory "+path);
            } catch (IOException e) {
            	logger.error("> Images: ERROR: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void ifExistsDelete(Path path) {
    	if (Files.exists(path)) {
            try {
				this.deleteDirectory(path);
			} catch (IOException e) {
				logger.error("> Images: ERROR: Failed to delete existing folders"+e.getMessage());
				e.printStackTrace();
			}
        }
    }
	
	private void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walk(directory)
                    .map(Path::toFile)
                    .forEach(File::delete);
            logger.warn("> Images: Deleted existing directory at: " + directory);
        }
    }

	private String getArticlePath(Article art) {
		return this.imageDirectory+"/"+art.getIdArticle()+"/images/";
	}
	
	
	
	
	// DEV AND PROD !!!!
	public void saveNewImage(MultipartFile imageFile, Article article) {
		Path auctionImageDir = Paths.get(getArticlePath(article));
		this.ifExistsDelete(auctionImageDir);
		this.ifNotExistsCreate(auctionImageDir);
		
        String filename = imageFile.getOriginalFilename(); //TODO should check and process it, do something with it
        Path targetPath = auctionImageDir.resolve(this.defaultImgName);

        try {
			imageFile.transferTo(targetPath);
		} catch (IllegalStateException e) {
			logger.error("Image: Save New: ERROR: "+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Image: Save New: ERROR: "+e.getMessage());
			e.printStackTrace();
		}

        // update the java attribute, need to send to database in parent calling function
        article.setImgFileName(this.defaultImgName);
	}
	
	
	
	
	
	
	
	
	
}
