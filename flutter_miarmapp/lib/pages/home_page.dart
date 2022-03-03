import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_publicacion/publicacion_bloc.dart';
import 'package:flutter_miarmapp/repository/imp/publicacion_repository.imp.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/utils/publicaciones_builder.dart';
import 'package:flutter_miarmapp/widgets/home_app_bar.dart';

class HomePageBloc extends StatelessWidget {
  HomePageBloc({Key? key}) : super(key: key);
  final PublicacionRepository publicacionRepository =
      PublicacionRepositoryImp();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const HomeAppBar(),
      body: BlocProvider(
        create: (context) {
          return PublicacionBloc(publicacionRepository)
            ..add(const GetPublicacioesPublicasEvent());
        },
        child: Column(
          children: [
            Row(
              children: [],
            ),
            BlocBuilder<PublicacionBloc, PublicacionState>(
                builder: (context, state) {
              if (state is GetPublicacioesPublicasState) {
                if (state.publicacionesPublicas.length == 0) {
                  return Container(
                    margin: EdgeInsets.only(top: 300),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text('No hay publicaciones aún'),
                        Text('Sé el primero en crear la tuya!')
                      ],
                    ),
                  );
                }
                return publicacionesBuilder(state.publicacionesPublicas);
              } else if (state is GetPublicacioesStateError) {
                return Text(state.mensaje);
              } else {
                return const Text('Error');
              }
            })
          ],
        ),
      ),
    );
  }
}
