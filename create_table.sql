-- Create syntax for TABLE 'article_tags'
CREATE TABLE `article_tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(11) unsigned NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'articles'
CREATE TABLE `articles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `body` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'tags'
CREATE TABLE `tags` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'users'
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL DEFAULT '',
  `gender` int(1) unsigned DEFAULT NULL,
  `attr1` int(11) DEFAULT NULL,
  `attr2` int(11) DEFAULT NULL,
  `attr3` int(11) DEFAULT NULL,
  `attr4` int(11) DEFAULT NULL,
  `attr5` int(11) DEFAULT NULL,
  `attr6` int(11) DEFAULT NULL,
  `attr7` int(11) DEFAULT NULL,
  `attr8` int(11) DEFAULT NULL,
  `attr9` int(11) DEFAULT NULL,
  `attr10` int(11) DEFAULT NULL,
  `attr11` int(11) DEFAULT NULL,
  `attr12` int(11) DEFAULT NULL,
  `attr13` int(11) DEFAULT NULL,
  `attr14` int(11) DEFAULT NULL,
  `attr15` int(11) DEFAULT NULL,
  `attr16` int(11) DEFAULT NULL,
  `attr17` int(11) DEFAULT NULL,
  `attr18` int(11) DEFAULT NULL,
  `attr19` int(11) DEFAULT NULL,
  `attr20` int(11) DEFAULT NULL,
  `attr21` int(11) DEFAULT NULL,
  `attr22` int(11) DEFAULT NULL,
  `attr23` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;