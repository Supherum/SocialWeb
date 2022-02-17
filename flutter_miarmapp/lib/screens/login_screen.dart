
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  Widget build(BuildContext context) {
    double withTotal = MediaQuery.of(context).size.width;
    double heightTotal = MediaQuery.of(context).size.height;
    String dropdownValue = 'ENGLISH';

    return Scaffold(
        body: Center(
      child: Padding(
          padding: const EdgeInsets.all(40.0),
          child: Column(children: [
            DropdownButton<String>(
              value: dropdownValue,
              icon: const Icon(Icons.arrow_drop_down_sharp),
              elevation: 16,
              style: const TextStyle(color: Colors.deepPurple),
              underline: Container(
                height: 2,
                color: Colors.deepPurpleAccent,
              ),
              onChanged: (String? newValue) {
                setState(() {
                  dropdownValue = newValue!;
                });
              },
              items: <String>['ENGLISH', 'ESPANISH']
                  .map<DropdownMenuItem<String>>((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
            ),
            Container(
                margin: EdgeInsets.only(top: heightTotal / 6),
                child: Text(
                  'InstaFake',
                  style: titleInstafake,
                )),
            Container(
              margin: const EdgeInsets.only(top: 40),
              child: Stack(
                children: [
                  Container(
                    width: withTotal,
                    height: 40,
                    decoration: BoxDecoration(
                        color: facebookColor,
                        borderRadius: BorderRadius.all(Radius.circular(3))),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Icon(
                          FontAwesomeIcons.facebookSquare,
                          color: Colors.white,
                        ),
                        Container(
                            margin: EdgeInsets.only(left: 5),
                            child: Text(
                              'Continue with Facebook',
                              style: loginSocialWeb,
                            ))
                      ],
                    ),
                  ),
                  Positioned.fill(
                      child: Material(
                          color: Colors.transparent,
                          child: InkWell(
                            splashColor: Colors.blue,
                            onTap: () => {},
                          ))),
                ],
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                SizedBox(
                  width: withTotal * 1 / 3,
                  child: Divider(
                    height: 1,
                    color: grey,
                  ),
                ),
                Container(
                    margin: EdgeInsets.only(top: 30, bottom: 30),
                    child: Text(
                      'OR',
                      style:
                          TextStyle(fontWeight: FontWeight.w600, color: grey),
                    )),
                SizedBox(
                  width: withTotal * 1 / 3,
                  child: Divider(
                    height: 1,
                    color: grey,
                  ),
                ),
              ],
            ),
            Container(
                child: TextField(
              decoration: InputDecoration(
                  border: OutlineInputBorder(),
                  labelText: 'Phone number, username or email',
                  filled: true,
                  fillColor: lightGrey),
            )),
            Container(
                margin: EdgeInsets.only(top: 5),
                child: TextField(
                  style: TextStyle(color: grey),
                  decoration: InputDecoration(
                      border: OutlineInputBorder(
                          borderSide: BorderSide(color: Colors.red, width: 1)),
                      filled: true,
                      fillColor: lightGrey),
                ))
          ])),
    ));
  }
}
