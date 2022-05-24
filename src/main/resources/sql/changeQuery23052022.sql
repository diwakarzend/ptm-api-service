#table ptm_ptp_detail  
CREATE TABLE `ptm_api_service`.`ptm_ptp_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_uuid` VARCHAR(100) NULL,
  `vendor_id` VARCHAR(100) NULL,
  `qr_details` VARCHAR(100) NULL,
  `total_limit` INT NOT NULL,
  `daily_limit` INT NOT NULL,
  `running_date` DATETIME NULL,
  `created_date` DATETIME NULL,
  `created_by` VARCHAR(100) NULL,
  `updated_date` DATETIME NULL,
  `updated_by` VARCHAR(100) NULL,
  `is_deleted` ENUM('Y', 'N') NULL,
  `is_status` ENUM('Y', 'N') NULL,
  `is_published` ENUM('Y', 'N') NULL,
  PRIMARY KEY (`id`));
