import 'package:flutter/material.dart';
import 'package:flutter_fakeinsta/pages/home_page.dart';
import 'package:flutter_fakeinsta/pages/login_page.dart';
import 'package:flutter_fakeinsta/pages/menu_page.dart';
import 'package:flutter_fakeinsta/pages/profile_page.dart';
import 'package:flutter_fakeinsta/pages/register_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'InstaFake',
      initialRoute: "/login",
      routes: {
        "/home":(context)=> const HomePage(),
        "/login":(context)=> const LoginPage(),
        "/register":(context)=> const RegisterPage(),
        "/profile":(context)=> const ProfilePage(),
        "/menu":(context)=> const MenuPage(),
      },
    );
  }
}

