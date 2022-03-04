import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/pages/login_page.dart';
import 'package:flutter_miarmapp/pages/menu_page.dart';
import 'package:flutter_miarmapp/pages/register_page.dart';
import 'package:flutter_miarmapp/pages/register_publicacion_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'InstaFake',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/login',
      routes: {
        '/': (context) => const MenuPage(),
        '/login': (context) => const LoginPage(),
        '/register': (context) => const RegisterPage(),
        '/register/publicacion': (context) => const RegisterPublicacionPage(),
      },
    );
  }
}
