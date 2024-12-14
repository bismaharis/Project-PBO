-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2024 at 08:16 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `racingcar`
--

-- --------------------------------------------------------

--
-- Table structure for table `scores`
--

CREATE TABLE `scores` (
  `id` int(11) NOT NULL,
  `player_name` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `scores`
--

INSERT INTO `scores` (`id`, `player_name`, `score`) VALUES
(1, 'dewi', 7),
(2, 'dewi', 22),
(3, 'cobaa', 6),
(4, 'veretrf', 9),
(5, 'dewww', 0),
(6, 'daewewew', 0),
(7, 'dewi agustin', 0),
(8, 'adsadsadasd', 0),
(9, 'dewi', 1),
(10, 'dewi', 1),
(11, 'dadad', 5),
(12, 'bhbbbbb', 4),
(13, 'daadada', 0),
(14, 'adaddad', 5),
(15, 'adada', 2),
(16, 'adadad', 9),
(17, 'dadadsdsd', 2),
(18, 'adadadad', 0),
(19, 'adsadsdsd', 8),
(20, 'addad', 3),
(21, 'adasdad', 4),
(22, 'adadasdsd', 0),
(23, 'hbhh', 0),
(24, 'jhjhjjh', 0),
(26, 'adsdadas', 0),
(27, 'dsadsdsad', 5),
(28, 'adasdasdsad', 0),
(29, 'adsddsad', 7),
(30, 'adasasdsad', 0),
(31, 'dsdsdadsd', 0),
(32, 'dasdad', 35),
(33, 'gjjhghghg', 1),
(34, 'gfgfg', 17),
(35, 'addwewewew', 0),
(36, 'agustina', 3),
(37, 'dewi agustinas', 2),
(38, 'dewi agustina', 2),
(39, 'mm', 9),
(40, 'gacorr', 10),
(41, 'hhh', 5);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `scores`
--
ALTER TABLE `scores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
