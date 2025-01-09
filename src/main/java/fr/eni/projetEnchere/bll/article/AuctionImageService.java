package fr.eni.projetEnchere.bll.article;

import org.springframework.web.multipart.MultipartFile;

import fr.eni.projetEnchere.bo.Article;

public interface AuctionImageService {

	void saveNewImage(MultipartFile imageFile, Article article);

	void startUpSequence();

}
