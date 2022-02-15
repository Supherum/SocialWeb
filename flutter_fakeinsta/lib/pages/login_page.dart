import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({ Key? key }) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: Column(
        children: [
          Text('InstaFake'),
          Container(
            decoration: BoxDecoration(
              color: Colors.blueAccent,
              borderRadius: BorderRadius.circular(10) 
            ),
            width: MediaQuery.of(context).size.width*8/10,
            height: 50,
            child: Row(
              children: [
                Image.asset('assets/images/foto.jpg')
              ],
            )
          )
        ],
      ),),
    );
  }
}