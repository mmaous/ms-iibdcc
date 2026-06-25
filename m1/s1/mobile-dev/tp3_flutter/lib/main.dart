import 'package:flutter/material.dart';
import 'quiz.dart';
import 'weather.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false, // Cleaner look for presentation
      title: 'TP3 Flutter',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const DefaultTabController(
        length: 2,
        child: Scaffold(
          appBar: TabBar(
            labelColor: Colors.blue, // Improved styling
            unselectedLabelColor: Colors.grey,
            tabs: [
              Tab(icon: Icon(Icons.quiz), text: "Quiz"),
              Tab(icon: Icon(Icons.cloud), text: "Weather"),
            ],
          ),
          body: TabBarView(
            children: [
              QuizScreen(), // Calls the Quiz feature
              WeatherScreen(), // Calls the Weather feature
            ],
          ),
        ),
      ),
    );
  }
}