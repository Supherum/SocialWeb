

import 'package:flutter_miarmapp/models/publicacion_response.dart';

abstract class PublicacionRepository {

  Future <List<PublicacionResponse>> getAllPublicacionesPublicas ();


}