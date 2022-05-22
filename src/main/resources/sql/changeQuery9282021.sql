 
  CREATE TABLE `ptm_api_service`.`ptm_user_fin_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_uuid` VARCHAR(100) NULL,
  `brand_name` VARCHAR(100) NULL,
  `register_company` VARCHAR(500) NULL,
  `register_address` VARCHAR(500) NULL,
  `gst_no` VARCHAR(45) NULL,
  `website` VARCHAR(500) NULL,
  `created_date` DATETIME NULL,
  `created_by` VARCHAR(100) NULL,
  `updated_date` DATETIME NULL,
  `updated_by` VARCHAR(100) NULL,
  `is_deleted` ENUM('Y', 'N') NULL,
  `is_status` ENUM('Y', 'N') NULL,
  `is_published` ENUM('Y', 'N') NULL,
  PRIMARY KEY (`id`));