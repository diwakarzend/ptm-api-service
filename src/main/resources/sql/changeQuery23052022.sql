#table ptm_ptp_detail  
CREATE TABLE `ptm_api_service`.`ptm_ptp_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_uuid` VARCHAR(100) NULL,
  `vendor_id` VARCHAR(100) NULL,
  `qr_details` VARCHAR(100) NULL,
  `total_limit` DOUBLE(10,2) NOT NULL,
  `daily_limit` DOUBLE(10,2) NOT NULL,
  `running_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` VARCHAR(100) NULL,
  `updated_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NULL,
  `is_deleted` ENUM('Y', 'N') NULL,
  `is_status` ENUM('Y', 'N') NULL,
  `is_published` ENUM('Y', 'N') NULL,
  PRIMARY KEY (`id`));
  
   CREATE TABLE `ptm_api_service`.`payment_brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brand_name` VARCHAR(100) NULL,
  `register_company` VARCHAR(500) NULL,
  `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `created_by` VARCHAR(100) NULL,
  `updated_date` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(100) NULL,
  `is_deleted` ENUM('Y', 'N') NULL,
  `is_status` ENUM('Y', 'N') NULL,
  `is_published` ENUM('Y', 'N') NULL,
  PRIMARY KEY (`id`));
  
  
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('PAYTM', 'PAYTM');
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('PHONEPE', 'PHONEPE');
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('GPAY', 'GPAY');
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('PAY2MOBILES', 'PAY2MOBILES');
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('RAZORPAY', 'RAZORPAY');
  INSERT INTO `payment_brand` (`brand_name`, `register_company`) VALUES ('BHIM', 'BHIM');
