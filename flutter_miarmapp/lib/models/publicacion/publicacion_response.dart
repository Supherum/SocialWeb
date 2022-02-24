class PublicacionResponse {
  PublicacionResponse({
    required this.id,
    required this.idUsuario,
    required this.nick,
    required this.fotoPerfil,
    required this.titulo,
    required this.descipcion,
    required this.listaRecursos,
    required this.private,
  });
  late final String id;
  late final String idUsuario;
  late final String nick;
  late final String fotoPerfil;
  late final String titulo;
  late final String descipcion;
  late final List<String> listaRecursos;
  late final bool private;

  PublicacionResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    idUsuario = json['idUsuario'];
    nick = json['nick'];
    fotoPerfil = json['fotoPerfil'];
    titulo = json['titulo'];
    descipcion = json['descipcion'];
    listaRecursos = List.castFrom<dynamic, String>(json['listaRecursos']);
    private = json['private'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['idUsuario'] = idUsuario;
    _data['nick'] = nick;
    _data['fotoPerfil'] = fotoPerfil;
    _data['titulo'] = titulo;
    _data['descipcion'] = descipcion;
    _data['listaRecursos'] = listaRecursos;
    _data['private'] = private;
    return _data;
  }
}
