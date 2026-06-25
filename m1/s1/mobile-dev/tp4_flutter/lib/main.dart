import 'package:flutter/material.dart';
import 'home.page.dart';
import 'counter.page.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'TP4 Flutter',
      theme: ThemeData(primarySwatch: Colors.deepOrange),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomePage(),
        '/counter': (context) => const CounterPage(),
      },
    );
  }
}