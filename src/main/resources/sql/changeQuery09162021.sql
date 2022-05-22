ALTER TABLE `user` 
CHANGE COLUMN `parent_id` `parent_id` VARCHAR(100) NULL DEFAULT NULL ;

INSERT INTO `role` (`id`, `name`) VALUES ('2', 'PTM_SUB_ADMIN');
INSERT INTO `role` (`id`, `name`) VALUES ('3', 'PTM_COMPANY');
