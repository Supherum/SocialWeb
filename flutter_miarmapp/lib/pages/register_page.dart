import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_login/login_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_register/register_bloc.dart';
import 'package:flutter_miarmapp/models/auth/register_dto.dart';
import 'package:flutter_miarmapp/pages/home_page.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:flutter_miarmapp/repository/imp/auth_repository_imp.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_miarmapp/utils/preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({Key? key}) : super(key: key);

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  XFile? file;
  DateTime? birthday;
  Color avatarColor = instagramLogin;
  Color birthdayColor = instagramLogin;

  ImagePicker picker = ImagePicker();
  final _formKey = GlobalKey<FormState>();
  late final AuthRepository authRepository;
  TextEditingController nickController = TextEditingController();
  TextEditingController nameController = TextEditingController();
  TextEditingController lastNameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController password2Controller = TextEditingController();

  @override
  void initState() {
    authRepository = AuthRepositoryImp();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return RegisterBloc(authRepository);
      },
      child: _registerBlocLogic(),
    );
  }

  _registerBlocLogic() {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: BlocConsumer<RegisterBloc, RegisterState>(
        listenWhen: (context, state) {
          return state is RegisterSuccessState ||
              state is RegisterErrorState ||
              state is RegisterImageErrorState ||
              state is RegisterDateErrorState;
        },
        listener: (context, state) {
          if (state is RegisterSuccessState) {
            PreferenceUtils.setString("jwtToken", state.loginResponse.tokenJwt);
            PreferenceUtils.setString("nombre", state.loginResponse.nombre);
            PreferenceUtils.setString(
                "apellidos", state.loginResponse.apellidos);
            PreferenceUtils.setString(
                "fotoPerfil", state.loginResponse.fotoPerfil);
            PreferenceUtils.setBool("isPrivate", state.loginResponse.private);
            PreferenceUtils.setString("nick", state.loginResponse.nick);
            PreferenceUtils.setString(
                "fechaNacimiento", state.loginResponse.fechaDeNacimiento);
            Navigator.push(context,
                MaterialPageRoute(builder: (context) => HomePageBloc()));
          } else if (state is RegisterErrorState) {
            _errorMessage(context, state.message);
          } else if (state is RegisterImageErrorState) {
            avatarColor = instagramBlue;
            _errorMessage(context, state.message);
          } else if (state is RegisterDateErrorState) {
            birthdayColor = instagramBlue;
            _errorMessage(context, state.message);
          }
        },
        buildWhen: (context, state) {
          return state is RegisterDateSuccessState ||
              state is RegisterImageSuccessState ||
              state is LoginInitialState ||
              state is LoginLoadingState;
        },
        builder: (context, state) {
          if (state is LoginInitialState) {
            return _formBuild('ENGLISH', context);
          } else if (state is LoginLoadingState) {
            return const CircularProgressIndicator();
          } else if (state is RegisterImageSuccessState) {
            avatarColor = lightGreen;
            file = state.file;
            return _formBuild('ENGLISH', context);
          } else if (state is RegisterDateSuccessState) {
            birthdayColor = lightGreen;
            birthday = state.birthday;
            return _formBuild('ENGLISH', context);
          } else {
            return _formBuild('ENGLISH', context);
          }
        },
      ),
    );
  }

  _formBuild(String dropdownValue, BuildContext context) {
    double withTotal = MediaQuery.of(context).size.width;
    return Form(
      key: _formKey,
      child: Center(
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
                        BlocProvider.of<RegisterBloc>(context)
                            .add(const SelectImageEvent(ImageSource.gallery))
                      },
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Container(
                              margin: const EdgeInsets.only(left: 5),
                              child: Text(
                                'Select your avatar',
                                style: loginSocialWeb,
                              ))
                        ],
                      ),
                      style: ElevatedButton.styleFrom(primary: avatarColor),
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
                            controller: nickController,
                            decoration: InputDecoration(
                                focusedBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(5),
                                  borderSide: BorderSide(
                                      color: instagramBlue, width: 2),
                                ),
                                enabledBorder: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(5),
                                    borderSide:
                                        BorderSide(color: lightGrey, width: 2)),
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
                                primary: birthdayColor),
                            onPressed: () {
                              BlocProvider.of<RegisterBloc>(context)
                                  .add(SelectDateEvent(context));
                            },
                            child: const Text("Birthday"),
                          ))
                    ],
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 7),
                      height: 50,
                      child: TextField(
                        controller: nameController,
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
                      margin: const EdgeInsets.only(top: 7),
                      height: 50,
                      child: TextField(
                        controller: lastNameController,
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
                        controller: passwordController,
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
                        controller: password2Controller,
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
                      margin: const EdgeInsets.only(top: 10),
                      width: withTotal,
                      child: ElevatedButton(
                        onPressed: () {
                          if (birthday != null && file != null) {
                            final formato=DateFormat('y-MM-dd');
                            final registerDto = RegisterDto(
                                nick: nickController.text,
                                nombre: nameController.text,
                                apellidos: lastNameController.text,
                                password: passwordController.text,
                                password2: password2Controller.text,
                                fechaDeNacimiento:formato.format(birthday!));
                            BlocProvider.of<RegisterBloc>(context)
                                .add(DoRegisterEvent(file!, registerDto));
                          } else {
                            _errorMessage(context,
                                'Los campos de avatar o cumpleños no pueden ser nulos');
                          }
                        },
                        child: const Text('Continue'),
                        style: ElevatedButton.styleFrom(primary: instagramBlue),
                      )),
                  Container(
                    margin: const EdgeInsets.only(top: 10),
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
      ),
    );
  }

  void _errorMessage(BuildContext context, message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
}
