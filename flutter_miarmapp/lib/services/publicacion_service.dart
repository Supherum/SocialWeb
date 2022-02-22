import 'dart:convert';

import 'package:flutter_miarmapp/models/publicacion_response.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;

class PublicacionService {
  Future<List<PublicacionResponse>> allPublicPublicaciones() async {
    var response = await http
        .get(Uri.parse('$baseUrl/public'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      List<PublicacionResponse> lista;
      lista = (jsonDecode(response.body) as List)
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
      return lista;
    } else {
      throw Exception('PublicacionServiceException');
    }
  }
}
