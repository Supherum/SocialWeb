import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
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
        resizeToAvoidBottomInset: false,
        body: Center(
          child: Padding(
              padding: const EdgeInsets.all(40.0),
              child: Stack(
                children: [
                  Column(children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SvgPicture.asset('assets/icons/more.svg'),
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
                          items: <String>['ENGLISH', 'SPANISH']
                              .map<DropdownMenuItem<String>>((String value) {
                            return DropdownMenuItem<String>(
                              value: value,
                              child: Text(value),
                            );
                          }).toList(),
                        ),
                        Container()
                      ],
                    ),
                    Container(
                      margin: EdgeInsets.only(top: heightTotal / 8),
                      child: SvgPicture.asset(
                        'assets/images/logo.svg',
                        color: Colors.black87,
                        height: 70,
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: ElevatedButton(
                        onPressed: () => {},
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Icon(
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
                        style: ElevatedButton.styleFrom(primary: instagramBlue),
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SizedBox(
                          width: withTotal * 1 / 3,
                          child: Divider(
                            thickness: 1,
                            color: lightGrey,
                          ),
                        ),
                        Container(
                            margin: const EdgeInsets.only(top: 30, bottom: 30),
                            child: Text(
                              'OR',
                              style: TextStyle(
                                  fontWeight: FontWeight.w600, color: grey),
                            )),
                        SizedBox(
                          width: withTotal * 1 / 3,
                          child: Divider(
                            thickness: 1,
                            color: lightGrey,
                          ),
                        ),
                      ],
                    ),
                    SizedBox(
                        height: 50,
                        child: TextField(
                          decoration: InputDecoration(
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5),
                                borderSide:
                                    BorderSide(color: instagramBlue, width: 2),
                              ),
                              enabledBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(5),
                                  borderSide:
                                      BorderSide(color: lightGrey, width: 2)),
                              labelText: 'Phone number, username, or email',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                        margin: const EdgeInsets.only(top: 7, bottom: 20),
                        height: 50,
                        child: TextField(
                          obscureText: true,
                          decoration: InputDecoration(
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(5),
                                borderSide:
                                    BorderSide(color: instagramBlue, width: 2),
                              ),
                              enabledBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(5),
                                  borderSide:
                                      BorderSide(color: lightGrey, width: 2)),
                              labelText: 'Password',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    SizedBox(
                        width: withTotal,
                        child: ElevatedButton(
                          onPressed: () => Navigator.pushNamed(context, "/"),
                          child: const Text('Log in'),
                          style:
                              ElevatedButton.styleFrom(primary: instagramLogin),
                        )),
                    Container(
                      margin: const EdgeInsets.only(top: 15),
                      child: InkWell(
                        onTap: () => {},
                        child: Text(
                          'Forgot password?',
                          style: forgotPassword,
                        ),
                      ),
                    ),
                  ]),
                  Positioned(
                      bottom: 10,
                      width: withTotal * 4 / 5,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text(
                            "Don't have an account?",
                            style: TextStyle(color: grey, fontSize: 12),
                          ),
                          Container(
                            margin: const EdgeInsets.only(left: 10),
                            child: InkWell(
                              onTap: () =>
                                  {Navigator.pushNamed(context, "/register")},
                              child: Text(
                                'Sign up',
                                style: TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.w500,
                                    color: instagramBlue),
                              ),
                            ),
                          )
                        ],
                      ))
                ],
              )),
        ));
  }
}
