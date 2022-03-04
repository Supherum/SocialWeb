import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_registro_publicaciones/registro_publicaciones_bloc.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_create_dto.dart';
import 'package:flutter_miarmapp/pages/menu_page.dart';
import 'package:flutter_miarmapp/repository/imp/publicacion_repository.imp.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_miarmapp/utils/preferences.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';

class RegisterPublicacionPage extends StatefulWidget {
  const RegisterPublicacionPage({Key? key}) : super(key: key);

  @override
  State<RegisterPublicacionPage> createState() =>
      _RegisterPublicacionPageState();
}

class _RegisterPublicacionPageState extends State<RegisterPublicacionPage> {
  late final PublicacionRepository publicacionRepository;
  TextEditingController titleController = TextEditingController();
  TextEditingController descripcionController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  bool isPrivate = false;
  XFile? file;

  @override
  void initState() {
    PreferenceUtils.init();
    publicacionRepository = PublicacionRepositoryImp();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return RegistroPublicacionesBloc(publicacionRepository);
      },
      child: _registerBlocLogic(),
    );
  }

  _registerBlocLogic() {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: BlocConsumer<RegistroPublicacionesBloc, RegistroPublicacionesState>(
        listenWhen: (context, state) {
          return state is RegistroPublicacionErrorState ||
              state is RegistroPublicacionSuccessState ||
              state is RegisterImageErrorState;
        },
        listener: (context, state) {
          if (state is RegistroPublicacionSuccessState) {
            Navigator.push(
                context, MaterialPageRoute(builder: (context) => MenuPage()));
          } else if (state is RegisterImageErrorState) {
            _errorMessage(context, state.message);
          } else if (state is RegistroPublicacionErrorState) {
            _errorMessage(context, state.message);
          }
        },
        buildWhen: (context, state) {
          return state is RegistroPublicacionesInitialState ||
              state is RegisterImageSuccessState ||
              state is RegisterPublicacionesLoadingState;
        },
        builder: (context, state) {
          if (state is RegistroPublicacionesInitialState) {
            return _buildForm(context);
          } else if (state is RegisterImageSuccessState) {
            file = state.file;
            return _buildForm(context);
          } else {
            return _buildForm(context);
          }
        },
      ),
    );
  }

  void _errorMessage(BuildContext context, message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  Widget _buildForm(context) {
    return Form(
      key: _formKey,
      child: Stack(
        children: [
          Column(
            children: [
              Container(
                margin: const EdgeInsets.only(top: 30),
                padding: const EdgeInsets.all(30),
                child: Column(
                  children: [
                    SvgPicture.asset('assets/images/publicacion.svg'),
                    Container(
                        margin: const EdgeInsets.only(top: 20),
                        height: 50,
                        child: TextFormField(
                          controller: titleController,
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
                              labelText: 'Title',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                        margin: const EdgeInsets.only(top: 7),
                        height: 50,
                        child: TextFormField(
                          controller: descripcionController,
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
                              labelText: 'Description',
                              labelStyle: TextStyle(fontSize: 12, color: grey),
                              filled: true,
                              fillColor: lightestGrey),
                        )),
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: ElevatedButton(
                        onPressed: () => {
                          BlocProvider.of<RegistroPublicacionesBloc>(context)
                              .add(const SelectImageEvent(ImageSource.gallery))
                        },
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                                margin: const EdgeInsets.only(left: 5),
                                child: Text(
                                  'Select your photo',
                                  style: loginSocialWeb,
                                ))
                          ],
                        ),
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text('Private: '),
                        Checkbox(
                            value: isPrivate,
                            onChanged: (bool? value) {
                              setState(() {
                                isPrivate = value!;
                              });
                            })
                      ],
                    ),
                    Container(
                        margin: EdgeInsets.only(top: 30),
                        child: _showImageSelected()),
                  ],
                ),
              )
            ],
          ),
          Positioned(
            bottom: 20,
            right: 20,
            child: ElevatedButton(
              onPressed: () {
                if (file != null) {
                  final dto = PublicacionCreateDto(
                      descripcion: descripcionController.text,
                      titulo: titleController.text,
                      isPrivate: isPrivate);
                  BlocProvider.of<RegistroPublicacionesBloc>(context).add(
                      DoRegisterEvent(file!, dto,
                          PreferenceUtils.getString('jwtToken').toString()));
                }
              },
              child: const Icon(Icons.send),
              style: ButtonStyle(
                shape: MaterialStateProperty.all(CircleBorder()),
                padding: MaterialStateProperty.all(EdgeInsets.all(20)),
                backgroundColor:
                    MaterialStateProperty.all(Colors.blue), // <-- Button color
                overlayColor:
                    MaterialStateProperty.resolveWith<Color?>((states) {
                  if (states.contains(MaterialState.pressed))
                    return Colors.red; // <-- Splash color
                }),
              ),
            ),
          )
        ],
      ),
    );
  }

  _showImageSelected() {
    if (file != null) {
      return Image.file(
        File(file!.path),
        height: 300,
      );
    }
    return const Text('Upload your photo now !');
  }
}
