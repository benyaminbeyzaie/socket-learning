import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final StreamController<String> controller = StreamController<String>();
  late List<String> messages;

  @override
  void initState() {
    messages = <String>[];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Socket learning'),
      ),
      body: Column(
        children: [
          ElevatedButton(
            onPressed: () {
              startSocket();
            },
            child: const Text(
              "Start Socket",
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: messages.length,
              itemBuilder: (BuildContext context, int index) {
                return ListTile(
                  title: Text(messages[index]),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  void startSocket() {
    addMessage("Connecting...");
    try {
      Socket.connect("10.0.2.2", 8080).then((socket) async {
        // send hello world to socket
        addMessage("Connected!");
        addMessage("Listing to socket...");
        socket.listen((data) {
          addMessage("Received: ${String.fromCharCodes(data).trim()}");
        });

        socket.writeln("Hello from flutter");
        socket.flush();
        addMessage("Message is sent!");
      });
    } catch (e) {
      addMessage("Error: $e");
    }
  }

  void addMessage(String message) {
    print(message);
    messages.add(message);
    setState(() {});
  }
}
