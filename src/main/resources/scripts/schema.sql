


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

CREATE TABLE IF NOT EXISTS BIDS (
    idUser   		INTEGER NOT NULL,
    idArticle       INTEGER NOT NULL,
    bidDate     DATE NOT NULL,
	bidPrice  	INTEGER NOT NULL, 
	CONSTRAINT bids_pk PRIMARY KEY (idUser, idArticle), 
	CONSTRAINT bids_users_fk FOREIGN KEY (idUser) REFERENCES USERS (idUser), 
	CONSTRAINT bids_articles_fk FOREIGN KEY (idArticle) REFERENCES ARTICLES (idArticle)
);



CREATE TABLE IF NOT EXISTS RemovalPoints (
	idRemovalPoint        SERIAL NOT NULL,
    roadName              VARCHAR(30) NOT NULL,
    zipCode      		  VARCHAR(15) NOT NULL,
    townName              VARCHAR(30) NOT NULL, 
    CONSTRAINT removalPoints_pk PRIMARY KEY (idRemovalPoint)
);

CREATE TABLE IF NOT EXISTS ARTICLES (
    idArticle                     SERIAL NOT NULL,
    name		                  VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	auctionStartDate           	  DATE NOT NULL,
    auctionEndDate             	  DATE NOT NULL,
    startingPrice                 INTEGER,
    salePrice                     INTEGER,
    idVendor                	  INTEGER NOT NULL,
    idCategory                    INTEGER NOT NULL, 
    idRemovalPoint				  INTEGER NOT NULL, 
    CONSTRAINT articles_pk PRIMARY KEY (idArticle), 
    CONSTRAINT articles_users_fk FOREIGN KEY (idVendor) REFERENCES USERS (idUser), 
    CONSTRAINT articles_categories_fk FOREIGN KEY (idCategory) REFERENCES CATEGORIES (idCategory), 
    CONSTRAINT articles_removalPoints_fk FOREIGN KEY (idRemovalPoint) REFERENCES RemovalPoints (idRemovalPoint)
);



/*




*/
