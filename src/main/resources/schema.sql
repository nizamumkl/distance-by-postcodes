-- --------------------------------------------------------
--
-- Table structure for table `postcodes`
--

CREATE TABLE IF NOT EXISTS postcodes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  postcode VARCHAR(8) NOT NULL,
  latitude DECIMAL(10,7),
  longitude DECIMAL(10,7)
);
