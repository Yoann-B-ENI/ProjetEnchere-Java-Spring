


CREATE TABLE IF NOT EXISTS users (
    idUser		   	      SERIAL NOT NULL,
    userName              VARCHAR(30) NOT NULL,
    password     		  VARCHAR(30) NOT NULL,
    name                  VARCHAR(30) NOT NULL,
    firstName             VARCHAR(30) NOT NULL,
    email                 VARCHAR(20) NOT NULL,
    phoneNumber    	      VARCHAR(15),
    roadName              VARCHAR(30) NOT NULL,
    zipCode			      VARCHAR(10) NOT NULL,
    townName              VARCHAR(30) NOT NULL,
    credits           	  INTEGER NOT NULL,
    admin   			  BOOLEAN NOT NULL, 
    CONSTRAINT users_pk PRIMARY KEY (idUser)
);

CREATE TABLE IF NOT EXISTS CATEGORIES (
    idCategory   SERIAL NOT NULL,
    name         VARCHAR(30) NOT NULL, 
    CONSTRAINT categories_pk PRIMARY KEY (idCategory)
);

CREATE TABLE IF NOT EXISTS AUCTIONS (
    idUser   		INTEGER NOT NULL,
    idArticle       INTEGER NOT NULL,
    auctionEndDate     DATE NOT NULL,
	auctionPrice  	INTEGER NOT NULL, 
	CONSTRAINT auctions_pk PRIMARY KEY (idUser, idArticle)
);



/*

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)



CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 
    
    
    
*/
