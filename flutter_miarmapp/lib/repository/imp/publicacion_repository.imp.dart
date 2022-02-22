import 'dart:convert';

import 'package:flutter_miarmapp/models/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;

class PublicacionRepositoryImp extends PublicacionRepository{

  @override
  Future<List<PublicacionResponse>> getAllPublicacionesPublicas() async {
   var response = await http
        .get(Uri.parse('$baseUrl/publicacion/public'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
    } else {
      throw Exception('PublicacionRepositoryException');
    }
  }
  
}