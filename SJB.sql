CREATE DATABASE SJB;
USE SJB;

CREATE TABLE HotelLogin (
    loginID INT NOT NULL IDENTITY(10000001, 1) PRIMARY KEY,
    loginName NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    headshot VARBINARY(MAX),
    googleID VARCHAR(30),
	LineID VARCHAR(30),
);

CREATE TABLE Customer (
    loginID INT NOT NULL IDENTITY(10000001, 1) PRIMARY KEY,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
	CustomerName NVARCHAR(100) NOT NULL,
    sex NVARCHAR(10) NOT NULL,
    birthday DATE NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    headshot VARBINARY(MAX),
    country NVARCHAR(50) NOT NULL,
    city NVARCHAR(50) NOT NULL,
    region NVARCHAR(50) NOT NULL,
    street NVARCHAR(100),
    postalCode NVARCHAR(20) NOT NULL
);

CREATE TABLE Hotel (
    hotelID INT NOT NULL IDENTITY(10000001, 1) PRIMARY KEY,
    loginID INT NOT NULL,
    hotelName NVARCHAR(100) NOT NULL,
    hotelType NVARCHAR(50) NOT NULL,
    hotelStar NVARCHAR(10) NOT NULL,
    tel NVARCHAR(20) NOT NULL,
    country NVARCHAR(50) NOT NULL,
    city NVARCHAR(50) NOT NULL,
    region NVARCHAR(50) NOT NULL,
    street NVARCHAR(100),
    postalCode NVARCHAR(20) NOT NULL,
    state BIT NOT NULL,
    FOREIGN KEY (loginID) REFERENCES HotelLogin(loginID)
);

CREATE TABLE HotelDetail (
    hotelID INT NOT NULL PRIMARY KEY,
    mapURL NVARCHAR(100) NOT NULL,
    GUInumber NVARCHAR(40) NOT NULL,
    businessName NVARCHAR(100) NOT NULL,
    openYear INT NOT NULL,
    cleaningService BIT ,
    expressCheckin BIT ,
    counter24hr BIT ,
    freeWiFi BIT ,
    roomCard BIT ,
    noSmoking BIT , 
    petFriendly BIT ,
    petDeposit INT ,
    petCleaningFee INT ,
    reservationNotice NVARCHAR(1000),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
);

CREATE TABLE Room(
	hotelID INT NOT NULL ,
    productID VARCHAR(36) NOT NULL ,
    productName NVARCHAR(100) NOT NULL,
    productType VARCHAR(30) NOT NULL,	/* 房間類型:單?雙?套房? */
    price INT NOT NULL,
    quantity INT NOT NULL,  /* 飯店同類型房間數量 */
    dateStart DATE ,
    dateEnd DATE ,
    enableSwitch BIT NOT NULL,	/* 上下架的啟用開關 */
    capacity INT NOT NULL,	/* 房間最多容納人數 */

    productDescription NVARCHAR(max) ,
    score INT NOT NULL,
    checkInTime char(5) NOT NULL,
    checkOutTime char(5) NOT NULL,
    childExtraBed BIT ,
    childrenPrice INT ,	/* childExtraBed 的價格 */
    age INT , 	/* childExtraBed 的使用限制年齡 */
   

    privateBathroom BIT NOT NULL ,
    showerRoom BIT NOT NULL,
    PRIMARY KEY (productID),
	UNIQUE (hotelID, productID),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
);

CREATE TABLE RoomPicture (
    imgID INT IDENTITY(10000001, 1) NOT NULL,
    productID VARCHAR(36) NOT NULL ,
    imagePath VARBINARY(MAX) NOT NULL,
    PRIMARY KEY (imgID),
    FOREIGN KEY (productID) REFERENCES Room(productID)
);

CREATE TABLE OrderTable (
    orderID VARCHAR(36) NOT NULL PRIMARY KEY,
    loginID INT NOT NULL,
    orderStatus NVARCHAR(40) NOT NULL, /* 訂單狀態 */
    orderTime NVARCHAR(40) NOT NULL,  /* 顧客下訂單的時間 */
    updateTime NVARCHAR(40) NOT NULL, /* 訂單更新時間 */
    FOREIGN KEY (loginID) REFERENCES Customer(loginID),
);

CREATE TABLE OrderDetail (
	detailID INT NOT NULL IDENTITY(1, 1) PRIMARY KEY,
    orderID VARCHAR(36) NOT NULL ,
    productID VARCHAR(36) NOT NULL,
    uniPrice NVARCHAR(40) ,
    quantity INT ,			/* 房間數量 */
    payment NVARCHAR(40) ,  /* 支付方式(到付) */
    numPeople INT ,			/* 入住人數 */
    checkInTime DATE ,		/* 顧客入住日期 */
    checkOutTime DATE ,		/* 顧客退房日期 */
    evaluation NVARCHAR(40) , 
    comment NVARCHAR(40) ,
    FOREIGN KEY (orderID) REFERENCES OrderTable(orderID),
    FOREIGN KEY (productID) REFERENCES Room(productID)
);

/* OrderTable 跟 Room 的中間表*/
CREATE TABLE OrderAndRoom (
	orderID VARCHAR(36) NOT NULL ,
	productID VARCHAR(36) NOT NULL,
	FOREIGN KEY (orderID) REFERENCES OrderTable(orderID),
	FOREIGN KEY (productID) REFERENCES Room(productID)
);

ALTER TABLE Room
ADD discount DECIMAL(10, 2);

/* 每日房間數 */
CREATE TABLE RoomDailyStatus(
	statusID INT NOT NULL IDENTITY(1, 1) PRIMARY KEY,
	productID VARCHAR(36) NOT NULL,
    productType VARCHAR(30) NOT NULL,
	quantity INT NOT NULL,				/* 每日剩餘房間數 */
	statusDate DATE ,					/* 每日剩餘房間數的"每日"的日期 */
	FOREIGN KEY (productID) REFERENCES Room(productID)
);

ALTER TABLE OrderDetail
ADD orderName VARCHAR(255),orderPhone VARCHAR(15);

ALTER TABLE Customer
    ADD googleID varchar(30) ;

CREATE TABLE Cart (
    cartID INT NOT NULL IDENTITY(1, 1),
    loginID INT NOT NULL,
    hotelID INT NOT NULL ,
    productID VARCHAR(36) NOT NULL ,
    quantity INT NOT NULL,
    PRIMARY KEY (cartID),
	FOREIGN KEY (loginID) REFERENCES Customer(loginID),
	FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID),
    FOREIGN KEY (productID) REFERENCES Room(productID),
);

/* Cart 跟 Room 中間表 */
CREATE TABLE CartAndRoom (
    cartID INT NOT NULL IDENTITY(1, 1),
    productID VARCHAR(36) NOT NULL ,
	FOREIGN KEY (cartID) REFERENCES Cart(cartID),
    FOREIGN KEY (productID) REFERENCES Room(productID),
);
/* Cart 跟 Hotel 中間表 */
CREATE TABLE CartAndHotel (
    cartID INT NOT NULL IDENTITY(1, 1),
    hotelID INT NOT NULL ,
	FOREIGN KEY (cartID) REFERENCES Cart(cartID),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID),
);

ALTER TABLE Cart
ADD checkInDate DATE ,
    checkOutDate DATE;

/* 後臺通知 */
CREATE TABLE HotelNotification (
    hotelNoticeID INT NOT NULL IDENTITY(10000001, 1) PRIMARY KEY,
    hotelID INT NOT NULL,
    noticeTime NVARCHAR(40) NOT NULL,  
    content NVARCHAR(1000) NOT NULL,  
    status NVARCHAR(40) NOT NULL,
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
);

/* 聊天紀錄 */
CREATE TABLE Message (
    messageID INT NOT NULL IDENTITY(10000001, 1) PRIMARY KEY,
    hotelID INT NOT NULL,
	loginID INT NOT NULL,
    messageTime NVARCHAR(40) NOT NULL,  
    content NVARCHAR(1000) NOT NULL,
    sender nvarchar(25) NOT NULL,
    status NVARCHAR(40),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID),
	FOREIGN KEY (loginID) REFERENCES Customer(loginID),
);


