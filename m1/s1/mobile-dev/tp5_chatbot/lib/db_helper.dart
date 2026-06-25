import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class Message {
  final int? id;
  final String text;
  final bool isUser;

  Message({this.id, required this.text, required this.isUser});

  Map<String, dynamic> toMap() {
    return {'id': id, 'text': text, 'isUser': isUser ? 1 : 0};
  }

  factory Message.fromMap(Map<String, dynamic> map) {
    return Message(
      id: map['id'],
      text: map['text'],
      isUser: map['isUser'] == 1,
    );
  }
}

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('chat.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = join(dbPath, filePath);

    return await openDatabase(
      path,
      version: 1,
      onCreate: (db, version) async {
        await db.execute('''
          CREATE TABLE messages (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            text TEXT NOT NULL,
            isUser INTEGER NOT NULL
          )
        ''');
      },
    );
  }

  Future<Message> insertMessage(Message message) async {
    final db = await instance.database;
    final id = await db.insert('messages', message.toMap());
    return Message(id: id, text: message.text, isUser: message.isUser);
  }

  Future<List<Message>> fetchMessages() async {
    final db = await instance.database;
    final result = await db.query('messages', orderBy: 'id ASC');
    return result.map((json) => Message.fromMap(json)).toList();
  }
}
