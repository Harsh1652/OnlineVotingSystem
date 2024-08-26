-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 19, 2023 at 08:44 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onlinevotingsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `candidate_details`
--

CREATE TABLE `candidate_details` (
  `id` int(11) NOT NULL,
  `election_id` int(11) DEFAULT NULL,
  `candidate_name` varchar(255) DEFAULT NULL,
  `candidate_details` text DEFAULT NULL,
  `inserted_by` varchar(255) DEFAULT NULL,
  `inserted_on` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `candidate_details`
--

INSERT INTO `candidate_details` (`id`, `election_id`, `candidate_name`, `candidate_details`, `inserted_by`, `inserted_on`) VALUES
(11, 10, 'Ronit Mangal', 'Hard Working, Leadership', 'Ronit', '2023-05-16'),
(12, 10, 'Harsh Gupta', ' Problem-solving', 'Ronit', '2023-05-16'),
(13, 11, 'Vineet', 'Leadership', 'Ronit', '2023-05-16'),
(14, 10, 'Chetanya Singh', 'Marketing, Management', 'Ronit', '2023-05-16'),
(15, 11, 'Harshita Rathore', ' Problem-solving, Hardworking', 'Ronit', '2023-05-16'),
(16, 11, 'Prerak', 'Leadership, Problem- Solving', 'Ronit', '2023-05-16');

-- --------------------------------------------------------

--
-- Table structure for table `elections`
--

CREATE TABLE `elections` (
  `id` int(11) NOT NULL,
  `election_topic` varchar(255) DEFAULT NULL,
  `no_of_candidates` int(11) DEFAULT NULL,
  `starting_date` date DEFAULT NULL,
  `ending_date` date DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `inserted_by` varchar(255) DEFAULT NULL,
  `inserted_on` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `elections`
--

INSERT INTO `elections` (`id`, `election_topic`, `no_of_candidates`, `starting_date`, `ending_date`, `status`, `inserted_by`, `inserted_on`) VALUES
(10, 'BCA Elections', 3, '2023-05-16', '2023-05-20', 'Active', 'Ronit', '2023-05-16'),
(11, 'College President Elections', 4, '2023-05-13', '2023-05-26', 'Active', 'Ronit', '2023-05-16');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `contact_no` varchar(45) DEFAULT NULL,
  `password` text DEFAULT NULL,
  `user_role` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `contact_no`, `password`, `user_role`) VALUES
(3, 'Ronit', '001', '313ffd9a24d50c11cd742ee14bc31b27621c9558', 'Admin'),
(4, 'Harsh', '002', '313ffd9a24d50c11cd742ee14bc31b27621c9558', 'Admin'),
(5, 'voter1', '01', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(6, 'voter2', '02', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(7, 'voter3', '03', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(8, 'voter4', '04', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(9, 'voter5', '05', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(10, 'Ayush Mangal', '07', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter'),
(11, 'Sachin Singh', '08', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', 'Voter');

-- --------------------------------------------------------

--
-- Table structure for table `votings`
--

CREATE TABLE `votings` (
  `id` int(11) NOT NULL,
  `election_id` int(11) DEFAULT NULL,
  `voters_id` int(11) DEFAULT NULL,
  `candidate_id` int(11) NOT NULL,
  `vote_date` date DEFAULT NULL,
  `vote_time` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `votings`
--

INSERT INTO `votings` (`id`, `election_id`, `voters_id`, `candidate_id`, `vote_date`, `vote_time`) VALUES
(8, 10, 5, 11, '2023-05-16', '11:04:56 pm'),
(9, 11, 5, 13, '2023-05-16', '11:05:01 pm'),
(10, 10, 6, 11, '2023-05-16', '11:05:35 pm'),
(11, 11, 6, 16, '2023-05-16', '11:05:39 pm'),
(12, 10, 7, 12, '2023-05-16', '11:05:52 pm'),
(13, 11, 7, 15, '2023-05-16', '11:05:56 pm'),
(14, 10, 8, 11, '2023-05-16', '11:06:31 pm'),
(15, 11, 8, 16, '2023-05-16', '11:06:35 pm'),
(16, 10, 9, 14, '2023-05-16', '11:07:04 pm'),
(17, 11, 9, 13, '2023-05-16', '11:07:16 pm'),
(18, 10, 10, 11, '2023-05-16', '11:09:00 pm'),
(19, 11, 10, 15, '2023-05-16', '11:09:55 pm'),
(20, 10, 11, 12, '2023-05-16', '11:10:48 pm'),
(21, 11, 11, 13, '2023-05-16', '11:10:52 pm');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `candidate_details`
--
ALTER TABLE `candidate_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `elections`
--
ALTER TABLE `elections`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `votings`
--
ALTER TABLE `votings`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `candidate_details`
--
ALTER TABLE `candidate_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `elections`
--
ALTER TABLE `elections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `votings`
--
ALTER TABLE `votings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
