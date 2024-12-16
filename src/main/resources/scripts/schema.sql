

DROP TABLE IF EXISTS Bids;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS Members;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS RemovalPoints;




CREATE TABLE IF NOT EXISTS Members (
    idMember		   	      SERIAL NOT NULL,
    userName              VARCHAR(30) NOT NULL,
    password     		  VARCHAR(30) NOT NULL,
    name                  VARCHAR(30) NOT NULL,
    firstName             VARCHAR(30) NOT NULL,
    email                 VARCHAR(40) NOT NULL,
    phoneNumber    	      VARCHAR(15),
    roadName              VARCHAR(30) NOT NULL,
    zipCode			      VARCHAR(10) NOT NULL,
    townName              VARCHAR(30) NOT NULL,
    credits           	  INTEGER NOT NULL,
    admin   			  BOOLEAN NOT NULL, 
    CONSTRAINT members_pk PRIMARY KEY (idMember)
);

CREATE TABLE IF NOT EXISTS CATEGORIES (
    idCategory   SERIAL 	 NOT NULL,
    name         VARCHAR(30) NOT NULL, 
    CONSTRAINT categories_pk PRIMARY KEY (idCategory)
);

CREATE TABLE IF NOT EXISTS RemovalPoints (
	idRemovalPoint        SERIAL 	  NOT NULL,
    roadName              VARCHAR(30) NOT NULL,
    zipCode      		  VARCHAR(15) NOT NULL,
    townName              VARCHAR(30) NOT NULL, 
    CONSTRAINT removalPoints_pk PRIMARY KEY (idRemovalPoint)
);

CREATE TABLE IF NOT EXISTS ARTICLES (
    idArticle                     SERIAL NOT NULL,
    name		                  VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	auctionStartDate           	  TIMESTAMP NOT NULL,
    auctionEndDate             	  TIMESTAMP NOT NULL,
    startingPrice                 INTEGER,
    salePrice                     INTEGER,
    status						  VARCHAR(30) NOT NULL, 
    idVendor                	  INTEGER NOT NULL,
    idCategory                    INTEGER NOT NULL, 
    idRemovalPoint				  INTEGER NOT NULL, 
    CONSTRAINT articles_pk PRIMARY KEY (idArticle), 
    
    CONSTRAINT articles_members_fk FOREIGN KEY (idVendor) REFERENCES Members (idMember), 
    CONSTRAINT articles_categories_fk FOREIGN KEY (idCategory) REFERENCES CATEGORIES (idCategory), 
    CONSTRAINT articles_removalPoints_fk FOREIGN KEY (idRemovalPoint) REFERENCES RemovalPoints (idRemovalPoint), 
    
    CONSTRAINT articles_status_ck CHECK(status in ('created', 'auctionStarted', 'auctionEnded', 'removed')), 
    CONSTRAINT articles_dates_startEnd_ck CHECK (auctionStartDate < auctionEndDate), 
    CONSTRAINT articles_status_date_ck CHECK(
    	(status = 'created' AND CURRENT_DATE < auctionStartDate AND CURRENT_DATE < auctionEndDate) OR
    	(status = 'auctionStarted' AND auctionStartDate < CURRENT_DATE AND CURRENT_DATE < auctionEndDate) OR
    	(status = 'auctionEnded' AND auctionEndDate < CURRENT_DATE AND auctionStartDate < CURRENT_DATE) OR
    	(status = 'removed' AND CURRENT_DATE < auctionEndDate))
);


CREATE TABLE IF NOT EXISTS BIDS (
    idMember   		INTEGER NOT NULL,
    idArticle       INTEGER NOT NULL,
    bidDate     DATE NOT NULL,
	bidPrice  	INTEGER NOT NULL, 
	CONSTRAINT bids_pk PRIMARY KEY (idMember, idArticle), 
	
	CONSTRAINT bids_members_fk FOREIGN KEY (idMember) REFERENCES Members (idMember), 
	CONSTRAINT bids_articles_fk FOREIGN KEY (idArticle) REFERENCES ARTICLES (idArticle)
);








/*




*/
