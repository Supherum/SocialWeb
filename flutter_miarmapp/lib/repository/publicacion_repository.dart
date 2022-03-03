import 'package:flutter_miarmapp/models/publicacion/publicacion_create_dto.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:image_picker/image_picker.dart';

abstract class PublicacionRepository {
  Future<List<PublicacionResponse>> getAllPublicacionesPublicas();
  Future<List<PublicacionResponse>> getAllPublicacionesDeUnUsuario(String id);
  Future<List<PublicacionResponse>> getMisPublicaciones();
  Future<PublicacionResponse> getUnaPublicacion(String idPublicacion);
  Future<PublicacionResponse> createPublicacion(
      PublicacionCreateDto dto, XFile file, String token);
}
