import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  static const String _apiKey = "sk-XXX";
  static const String _baseUrl = "https://api.openai.com/v1/chat/completions";

  static Future<String> sendMessageToChatGPT(String prompt) async {
    try {
      final response = await http.post(
        Uri.parse(_baseUrl),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $_apiKey',
        },
        body: jsonEncode({
          'model': 'gpt-3.5-turbo',
          'messages': [
            {'role': 'user', 'content': prompt}
          ],
        }),
      );

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        return data['choices'][0]['message']['content'].toString().trim();
      } else {
        return "Server Error: ${response.statusCode}. Please verify your API key.";
      }
    } catch (e) {
      return "Network Error: Failed to connect to ChatGPT. ($e)";
    }
  }
}