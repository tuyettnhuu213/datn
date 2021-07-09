-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2021 at 11:20 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `utc2`
--

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `IdSP` int(11) NOT NULL,
  `HAnhSP` varchar(100) NOT NULL,
  `TenSP` varchar(50) NOT NULL,
  `GiaSP` int(11) NOT NULL,
  `TTrangSP` int(11) NOT NULL,
  `IdLoaiSP` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`IdSP`, `HAnhSP`, `TenSP`, `GiaSP`, `TTrangSP`, `IdLoaiSP`) VALUES
(0, '', '', 0, 1, 0),
(1, 'image/canhkhoqua.jpg', 'Canh khổ qua nhồi thịt', 20000, 1, 1),
(10, 'image/suonnuong.jpg', 'Sườn nướng', 22000, 1, 1),
(14, 'image/gachiennuocmam.jpg', 'Đùi gà chiên nước mắm', 22000, 1, 1),
(15, 'image/suonnuong.jpg', 'Sườn nướng', 22000, 1, 0),
(16, 'image/gachiennuocmam.jpg', 'Đùi gà chiên nước mắm', 22000, 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`IdSP`),
  ADD KEY `IdLoaiSP` (`IdLoaiSP`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `IdSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`IdLoaiSP`) REFERENCES `loaisp` (`IdLoaiSP`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
