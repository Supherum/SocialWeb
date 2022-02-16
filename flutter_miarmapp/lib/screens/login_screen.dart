import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({ Key? key }) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  Widget build(BuildContext context) {
    double withTotal=MediaQuery.of(context).size.width;
    double heightTotal=MediaQuery.of(context).size.height;

    return Scaffold(
      body: Center(child: 
      Padding(
        padding: const EdgeInsets.all(40.0),
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.only(top:heightTotal/6 ),
              child: Text('InstaFake',style: titleInstafake,)),
            
            Container(
              margin: EdgeInsets.only(top: 40),
              width: withTotal,
              height: 40,
              decoration: BoxDecoration(color: facebookColor,borderRadius: BorderRadius.all(Radius.circular(3))),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Image.asset('assets/images/logoFacebook.png',height: 30),
                  Container(margin: EdgeInsets.only(left: 5), child: Text('Continue with Facebook',style: loginSocialWeb,))
                ],
              ),
            ),
            Container(margin: EdgeInsets.only(top: 30), child: Text('OR',style:TextStyle(fontWeight: FontWeight.w600,color: grey) ,)),
            Container(
              child: TextField())
          ],
        ),
      ),),
    );
  }
}