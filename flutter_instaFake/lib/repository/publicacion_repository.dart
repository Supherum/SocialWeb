import 'package:flutter_miarmapp/models/publicacion/publicacion_create_dto.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:image_picker/image_picker.dart';

abstract class PublicacionRepository {
  Future<List<PublicacionResponse>> getAllPublicacionesPublicas(String token);
  Future<List<PublicacionResponse>> getAllPublicacionesDeUnUsuario(String id,String token);
  Future<List<PublicacionResponse>> getMisPublicaciones(String token);
  Future<PublicacionResponse> getUnaPublicacion(String idPublicacion,String token);
  Future<PublicacionResponse> createPublicacion(
      PublicacionCreateDto dto, XFile file, String token);
  
}
