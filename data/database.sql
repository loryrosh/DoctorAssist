/**
 * @author http://twin-persona.org
 *
 * SQL-file for the "doctor" database.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

--
-- Database name: `doctor`
--
-- --------------------------------------------------------
USE doctor;


--
-- Table structure `doctors`
--

CREATE TABLE IF NOT EXISTS `doctors`
(
  `id` int AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `surname` varchar(255) COLLATE utf8_bin NOT NULL,
  `profile` varchar(255) COLLATE utf8_bin NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE ( `name`, `surname` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `patients`
--

CREATE TABLE IF NOT EXISTS `patients`
(
  `id` int AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_bin NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `surname` varchar(255) COLLATE utf8_bin NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE ( `email` ),
  UNIQUE ( `name`, `surname` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `timetables`
--

CREATE TABLE IF NOT EXISTS `timetables`
(
  `id` int AUTO_INCREMENT,
  `time` TIME NOT NULL,
  `doctor_id` int NOT NULL COMMENT 'id in doctors',

  PRIMARY KEY (`id`),
  FOREIGN KEY( `doctor_id` ) REFERENCES doctors( `id` ),
  UNIQUE ( `doctor_id`, `time` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- --------------------------------------------------------

--
-- Table structure `appointments`
--

CREATE TABLE IF NOT EXISTS `appointments`
(
  `id` int AUTO_INCREMENT,
  `patient_id` int NOT NULL COMMENT 'id in patients',
  `timetable_id` int NOT NULL COMMENT 'id in timetables',

  PRIMARY KEY (`id`),
  FOREIGN KEY( `patient_id` ) REFERENCES patients( `id` ),
  FOREIGN KEY( `timetable_id` ) REFERENCES timetables( `id` ),
  UNIQUE ( `patient_id`, `timetable_id` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- --------------------------------------------------------

--
-- Values
--

INSERT INTO `doctors` VALUES (1,'Ivan','Petrov', 'Cardiologist'),(2,'Maria','Ivanova', 'Endocrinologist'),
  (3,'Jack','Smith', 'Neurologist');

INSERT INTO `patients` VALUES (1,'jack@mail.ru','123','Jack','Smith'),
  (2,'olga@mail.ru','123','Olga','Stein'),(3,'brian@mail.ru','123','Brian','Black');

INSERT INTO `timetables` VALUES (1,'10:20:00',1),(2,'11:40:00',1),
  (3,'15:30:00',2),(4,'16:00:00',3),(5,'10:30:00',2),(6,'10:50:00',3);
