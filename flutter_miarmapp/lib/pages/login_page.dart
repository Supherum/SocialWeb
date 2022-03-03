import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_login/login_bloc.dart';
import 'package:flutter_miarmapp/models/auth/login_dto.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:flutter_miarmapp/repository/imp/auth_repository_imp.dart';
import 'package:flutter_miarmapp/screens/menu_screen.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_miarmapp/utils/preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  late AuthRepository authRepository;
  final _fomrKey = GlobalKey<FormState>();
  TextEditingController nickController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  String dropdownValue = 'ENGLISH';

  @override
  void initState() {
    PreferenceUtils.init();
    authRepository = AuthRepositoryImp();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) {
          return LoginBloc(authRepository);
        },
        child: _loginBlocLogic(context));
  }

  Widget _loginBlocLogic(context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: BlocConsumer<LoginBloc, LoginState>(
        listenWhen: (context, state) {
          return state is LoginSuccessState || state is LoginErrorState;
        },
        listener: (context, state) {
          if (state is LoginSuccessState) {
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
            Navigator.push(
                context, MaterialPageRoute(builder: (context) => MenuScreen()));
          } else if (state is LoginErrorState) {
            _showSnackbar(context, state.mensaje);
          }
        },
        buildWhen: (context, state) {
          return state is LoginInitialState || state is LoginLoadingState;
        },
        builder: (context, state) {
          if (state is LoginInitialState) {
            return _loginForm(context);
          } else if (state is LoginLoadingState) {
            return const Center(child: CircularProgressIndicator());
          } else {
            return _loginForm(context);
          }
        },
      ),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  Widget _loginForm(context) {
    double withTotal = MediaQuery.of(context).size.width;
    double heightTotal = MediaQuery.of(context).size.height;

    return Form(
      key: _fomrKey,
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
                  TextFormField(
                    validator: (value) {
                      return value == null || value.isEmpty
                          ? 'Write a nickName'
                          : null;
                    },
                    controller: nickController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(5),
                          borderSide:
                              BorderSide(color: instagramBlue, width: 2),
                        ),
                        enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(5),
                            borderSide: BorderSide(color: lightGrey, width: 2)),
                        labelText: 'Phone number, username, or email',
                        labelStyle: TextStyle(fontSize: 12, color: grey),
                        filled: true,
                        fillColor: lightestGrey),
                  ),
                  Container(
                      margin: const EdgeInsets.only(top: 7, bottom: 20),
                      child: TextFormField(
                        validator: (value) {
                          return (value == null || value.isEmpty)
                              ? 'Write a password'
                              : null;
                        },
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
                  SizedBox(
                      width: withTotal,
                      child: ElevatedButton(
                        onPressed: () {
                          if (_fomrKey.currentState!.validate()) {
                            final loginDto = LoginDto(
                                nick: nickController.text,
                                password: passwordController.text);
                            BlocProvider.of<LoginBloc>(context)
                                .add(TryToLoginEvent(loginDto));
                          }
                        },
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
                                {Navigator.pushNamed(context, '/register')},
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
      ),
    );
  }
}
