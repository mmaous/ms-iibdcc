import 'package:flutter/material.dart';


class QuizScreen extends StatefulWidget {
  const QuizScreen({Key? key}) : super(key: key);

  @override
  _QuizScreenState createState() => _QuizScreenState();
}

class _QuizScreenState extends State<QuizScreen> {
  int _questionIndex = 0;
  int _totalScore = 0;

  // Improved: Using a List of Maps to structure the data cleanly
  final List<Map<String, Object>> _questions = const [
    {
      'questionText': 'What is the main language used in Flutter?',
      'answers': [
        {'text': 'Java', 'score': 0},
        {'text': 'Dart', 'score': 10},
        {'text': 'Kotlin', 'score': 0},
      ],
    },
    {
      'questionText': 'Which widget is used for flexible layouts?',
      'answers': [
        {'text': 'Container', 'score': 0},
        {'text': 'Expanded', 'score': 10},
        {'text': 'Text', 'score': 0},
      ],
    },
  ];

  void _answerQuestion(int score) {
    setState(() {
      _totalScore += score;
      _questionIndex++;
    });
  }

  void _resetQuiz() {
    setState(() {
      _questionIndex = 0;
      _totalScore = 0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return _questionIndex < _questions.length
        ? Quiz(
      questionData: _questions[_questionIndex],
      answerQuestion: _answerQuestion,
    )
        : Score(score: _totalScore, resetHandler: _resetQuiz);
  }
}


class Quiz extends StatelessWidget {
  final Map<String, Object> questionData;
  final Function(int) answerQuestion;

  const Quiz({Key? key, required this.questionData, required this.answerQuestion}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Question(questionData['questionText'] as String),
        const SizedBox(height: 20),
        ...(questionData['answers'] as List<Map<String, Object>>).map((answer) {
          return Answer(
                () => answerQuestion(answer['score'] as int),
            answer['text'] as String,
          );
        }).toList(),
      ],
    );
  }
}


class Question extends StatelessWidget {
  final String questionText;

  const Question(this.questionText, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.all(16),
      child: Text(
        questionText,
        style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
        textAlign: TextAlign.center,
      ),
    );
  }
}


class Answer extends StatelessWidget {
  final VoidCallback selectHandler;
  final String answerText;

  const Answer(this.selectHandler, this.answerText, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      margin: const EdgeInsets.symmetric(horizontal: 40, vertical: 5),
      // Improved: Replaced older RaisedButton with modern ElevatedButton
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          padding: const EdgeInsets.all(15),
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        ),
        onPressed: selectHandler,
        child: Text(answerText, style: const TextStyle(fontSize: 16)),
      ),
    );
  }
}


class Score extends StatelessWidget {
  final int score;
  final VoidCallback resetHandler;

  const Score({Key? key, required this.score, required this.resetHandler}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text(
            'Quiz Completed!',
            style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
          ),
          const SizedBox(height: 10),
          Text(
            'Your Score: $score',
            style: const TextStyle(fontSize: 24, color: Colors.blue),
          ),
          const SizedBox(height: 30),
          OutlinedButton(
            onPressed: resetHandler,
            child: const Text('Restart Quiz'),
          ),
        ],
      ),
    );
  }
}