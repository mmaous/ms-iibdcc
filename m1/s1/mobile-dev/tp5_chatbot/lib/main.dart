import 'package:flutter/material.dart';
import 'db_helper.dart';
import 'api_service.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'AI ChatBot',
      theme: ThemeData(primarySwatch: Colors.teal),
      home: const ChatScreen(),
    );
  }
}

class ChatScreen extends StatefulWidget {
  const ChatScreen({Key? key}) : super(key: key);

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final List<Message> _messages = [];
  final TextEditingController _controller = TextEditingController();
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _loadChatHistory();
  }

  Future<void> _loadChatHistory() async {
    final savedMessages = await DatabaseHelper.instance.fetchMessages();
    setState(() {
      _messages.addAll(savedMessages);
    });
  }

  Future<void> _handleSubmittedMessage(String text) async {
    if (text.trim().isEmpty) return;
    _controller.clear();

    Message userMsg = Message(text: text, isUser: true);
    userMsg = await DatabaseHelper.instance.insertMessage(userMsg);

    setState(() {
      _messages.add(userMsg);
      _isLoading = true;
    });

    final aiReplyText = await ApiService.sendMessageToChatGPT(text);

    Message aiMsg = Message(text: aiReplyText, isUser: false);
    aiMsg = await DatabaseHelper.instance.insertMessage(aiMsg);

    setState(() {
      _messages.add(aiMsg);
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('TP5 - AI ChatBot (!)'),
        backgroundColor: Colors.teal,
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.all(12.0),
              itemCount: _messages.length,
              itemBuilder: (context, index) {
                final msg = _messages[index];
                return _buildChatBubble(msg);
              },
            ),
          ),

          if (_isLoading)
            const Padding(
              padding: EdgeInsets.symmetric(vertical: 8.0),
              child: CircularProgressIndicator(color: Colors.teal),
            ),

          const Divider(height: 1.0),
          _buildInputArea(),
        ],
      ),
    );
  }

  Widget _buildChatBubble(Message message) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 6.0),
      alignment: message.isUser ? Alignment.centerRight : Alignment.centerLeft,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 14.0, vertical: 10.0),
        decoration: BoxDecoration(
          color: message.isUser ? Colors.teal[100] : Colors.grey[200],
          borderRadius: BorderRadius.only(
            topLeft: const Radius.circular(12),
            topRight: const Radius.circular(12),
            bottomLeft: Radius.circular(message.isUser ? 12 : 0),
            bottomRight: Radius.circular(message.isUser ? 0 : 12),
          ),
        ),
        constraints: BoxConstraints(
          maxWidth: MediaQuery.of(context).size.width * 0.75,
        ),
        child: Text(
          message.text,
          style: const TextStyle(fontSize: 16, color: Colors.black87),
        ),
      ),
    );
  }

  Widget _buildInputArea() {
    return Container(
      color: Colors.white,
      padding: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
      child: Row(
        children: [
          Expanded(
            child: TextField(
              controller: _controller,
              decoration: const InputDecoration.collapsed(
                hintText: 'Type a prompt...',
              ),
              textInputAction: TextInputAction.send,
              onSubmitted: _handleSubmittedMessage,
            ),
          ),
          IconButton(
            icon: const Icon(Icons.send, color: Colors.teal),
            onPressed: () => _handleSubmittedMessage(_controller.text),
          ),
        ],
      ),
    );
  }
}
