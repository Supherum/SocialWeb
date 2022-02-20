import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/models/publicacion_response.dart';
import 'package:flutter_miarmapp/services/publicacion_service.dart';
import 'package:flutter_miarmapp/utils/publicaciones_builder.dart';
import 'package:flutter_miarmapp/widgets/home_app_bar.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late Future<List<PublicacionResponse>> listaPublicaciones;

  @override
  void initState() {
    listaPublicaciones = PublicacionService().allPublicPublicaciones();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const HomeAppBar(),
      body: Column(
        children: [
          Row(
            children: [],
          ),
          Expanded(
            child: ListView(
              scrollDirection: Axis.vertical,
              physics: const AlwaysScrollableScrollPhysics(),
              children: [
                FutureBuilder<List<PublicacionResponse>>(
                    future: listaPublicaciones,
                    builder: ((context, snapshot) {
                      if (snapshot.hasData) {
                        return publicacionesBuilder(snapshot.data!);
                      }
                      if (snapshot.hasError) {
                        return Text(snapshot.error.toString());
                      } else {
                        return const CircularProgressIndicator();
                      }
                    }))
              ],
            ),
          )
        ],
      ),
    );
  }
}
