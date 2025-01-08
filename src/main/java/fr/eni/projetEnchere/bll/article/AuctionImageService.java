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

import fr.eni.projetEnchere.bo.Article;
import jakarta.annotation.PostConstruct;

@Service
public class AuctionImageService {
	
	private final String imageDirectory;
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
		Path path = Paths.get(imageDirectory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.debug("Startup: Images: created directory "+path);
            } catch (IOException e) {
            	logger.error("Startup: Images: ERROR: "+e.getMessage());
                e.printStackTrace();
            }
        }
        
        List<Article> articlesFound = articleService.getAll();
        for (Article art : articlesFound) {
			if (art.getImgFileName() == null) {continue;}
			this.startUpDirectory(formatDirectoryName(art), art.getImgFileName());
			art = articleService.getById(art.getIdArticle()); // doesn't work without
			art.setImgFileName(this.defaultImgName);
			articleService.update(art);
		}
		
	}
	
	private String formatDirectoryName(Article art) {
		return this.imageDirectory+"/"+art.getIdArticle()+"/images/";
	}
	
	private void startUpDirectory(String dirName, String imgFileName) {
		Path path = Paths.get(dirName);
        if (Files.exists(path)) {
            try {
				this.deleteDirectory(path);
			} catch (IOException e) {
				logger.error("Startup: Images: ERROR: Failed to delete existing folders"+e.getMessage());
				e.printStackTrace();
			}
        }
        
        try {
			Files.createDirectories(path);
	        logger.debug("Startup: Images: created directory "+path);
		} catch (IOException e) {
        	logger.error("Startup: Images: ERROR: Failed to set up folders"+e.getMessage());
            e.printStackTrace();
        }
        
        this.copyImageToFolder(imgFileName, dirName);
		
		// create folder structure
		// move correct image 
		
	}
	
	private void deleteDirectory(Path directory) throws IOException {
        if (Files.exists(directory)) {
            Files.walk(directory)
                    .map(Path::toFile)
                    .forEach(File::delete);
            logger.warn("Deleted existing directory at: " + directory);
        }
    }
	
	public void copyImageToFolder(String imgFileName, String destinationDir) {
        Path sourcePath = Paths.get("src/main/resources/static/test_images/"+imgFileName);
        Path destinationFolder = Paths.get(destinationDir);
        Path destinationFile = destinationFolder.resolve(this.defaultImgName);

        try {
            // Copy the image file to the new folder
            Files.copy(sourcePath, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            logger.debug("Startup: Images: Image "+imgFileName+" copied successfully to: " + destinationFile);

        } catch (IOException e) {
            logger.error("Startup: Images: ERROR: Failed to copy the image: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	
	
	
	
	
	
	
}
