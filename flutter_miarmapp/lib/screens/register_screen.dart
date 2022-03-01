import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:image_picker/image_picker.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  late DateTime _selectedDate = DateTime(2016, 12, 23);


ImagePicker picker = ImagePicker();
  @override
  Widget build(BuildContext context) {
    double withTotal = MediaQuery.of(context).size.width;
    String dropdownValue = 'ENGLISH';
    DateTime? picked;
    
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
                      margin: const EdgeInsets.only(top: 20),
                      child: SvgPicture.asset(
                        'assets/images/logo.svg',
                        color: Colors.black87,
                        height: 70,
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: ElevatedButton(
                        onPressed: () => {
                          picker.pickImage(source: ImageSource.gallery)
                        },
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            
                            Container(
                                margin: EdgeInsets.only(left: 5),
                                child: Text(
                                  'Select your avatar',
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
                              'AND',
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
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        SizedBox(
                            height: 50,
                            width: withTotal * 3 / 7.8,
                            child: TextField(
                              decoration: InputDecoration(
                                  focusedBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(5),
                                    borderSide: BorderSide(
                                        color: instagramBlue, width: 2),
                                  ),
                                  enabledBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5),
                                      borderSide: BorderSide(
                                          color: lightGrey, width: 2)),
                                  labelText: 'Nickname',
                                  labelStyle:
                                      TextStyle(fontSize: 12, color: grey),
                                  filled: true,
                                  fillColor: lightestGrey),
                            )),
                        SizedBox(
                            height: 50,
                            width: withTotal * 3 / 7.8,
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                  primary: instagramLogin),
                              onPressed: () async {
                                DateTime? _currentDate = await showDatePicker(
                                    context: context,
                                    initialDate: DateTime.now(),
                                    firstDate: DateTime(1920),
                                    lastDate: DateTime.now());
                                if (_currentDate == null) return;
                                setState(() => _selectedDate = _currentDate);
                              },
                              child: const Text("Birthday"),
                            ))
                      ],
                    ),
                    Container(
                        margin: EdgeInsets.only(top: 7),
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
                              labelText: 'Name',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                        margin: EdgeInsets.only(top: 7),
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
                              labelText: 'Lastname',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                        margin: const EdgeInsets.only(top: 7),
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
                    Container(
                        margin: const EdgeInsets.only(top: 7),
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
                              labelText: 'Repeat password',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                        margin: EdgeInsets.only(top: 10),
                        width: withTotal,
                        child: ElevatedButton(
                          onPressed: () => Navigator.pushNamed(context, "/"),
                          child: const Text('Continue'),
                          style:
                              ElevatedButton.styleFrom(primary: instagramBlue),
                        )),
                    Container(
                      margin: EdgeInsets.only(top: 10),
                      child: Text(
                        'By singing up, you agree to our',
                        style: TextStyle(color: grey, fontSize: 12),
                      ),
                    ),
                    Text(
                      'Terms & Privacity Policy',
                      style: TextStyle(color: grey, fontSize: 13),
                    )
                  ]),
                ],
              )),
        ));
  }
}
