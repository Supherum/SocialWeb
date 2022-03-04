class PublicacionCreateDto {
  PublicacionCreateDto({
    required this.titulo,
    required this.descripcion,
    required this.isPrivate,
  });
  late final String titulo;
  late final String descripcion;
  late final bool isPrivate;

  PublicacionCreateDto.fromJson(Map<String, dynamic> json) {
    titulo = json['titulo'];
    descripcion = json['descipcion'];
    isPrivate = json['isPrivate'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['titulo'] = titulo;
    _data['descripcion'] = descripcion;
    _data['isPrivate'] = isPrivate;
    return _data;
  }
}
