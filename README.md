# ProfInsight

This project is a Java-based data parsing and visualization application. The tool processes a real-world dataset of nearly 20,000 professor reviews from RateMyProfessors.com and enables interactive exploration of trends in ratings, language usage, and gender-based patterns.

The project emphasizes hash table implementation, efficient data analysis, and event-driven visualization. Users can input keywords or categories through a JavaFX GUI to dynamically generate bar and line graphs representing different distributions in the data.

# DEMO
[Click Here to view Live Demo](https://drive.google.com/file/d/1Ds_7BCYSS1StYsZjVk3yr-61W0zICEJZ/view?usp=sharing)

# Tech Stack and Core Concepts
**Language:** Java
**GUI:** JavaFX
**Core Concepts:** 
- Hash Tables and Collision Handling
- Data normalization and parsing
- Abstraction and polymorphism via abstract classes

# Project Structure
MyHashTable.java — Custom generic hash table implementation
DataAnalyzer.java — Abstract base class defining analysis interface
RatingDistributionByProf.java — Rating distribution for a given professor
- User Inputs professors first and last name to view their range of ratings (1-5) on Rate My Professor
RatingDistributionBySchool.java — Ratings aggregated by school
- User inputs school name to view average professor rating for all professors in the data set that correspond to inputted school
GenderByKeyword.java — Keyword frequency by perceived gender
- User inputs any keyword to view distribution between Female and Male Professors
RatingByKeyword.java — Rating distribution associated with keywords
- User inputs keyword to view distribution of ratings associated with said keyword
RatingByGender.java — Rating and difficulty distribution by gender
- User inputs word difficulty or quality to view distribution

# Learning Outcomes
- Designed and implemented a performant data structure for real-world data analysis
- Applied hashing to enable scalable, interactive queries
- Gained experience translating raw data into meaningful visual insights
- Strengthened understanding of abstraction, modular design, and defensive programming
