import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_svg/flutter_svg.dart';

class HomeAppBar extends StatelessWidget implements PreferredSizeWidget {
  const HomeAppBar({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          padding: const EdgeInsets.only(top: 10, left: 10),
          child: Column(
            children: [
              Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
                SvgPicture.asset(
                  'assets/images/logo2.svg',
                  color: Colors.black87,
                  height: 30,
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    IconButton(
                        onPressed: () {
                          Navigator.pushNamed(context, '/register/publicacion');
                        },
                        icon: SvgPicture.asset(
                          'assets/icons/add.svg',
                          color: Colors.black87,
                          height: 23,
                        )),
                    SizedBox(
                      width: 40,
                      child: IconButton(
                          icon: SvgPicture.asset(
                            'assets/icons/heart.svg',
                            color: Colors.black87,
                            height: 23,
                          ),
                          onPressed: () {}),
                    ),
                    IconButton(
                        onPressed: () {},
                        icon: SvgPicture.asset(
                          'assets/icons/messenger.svg',
                          color: Colors.black87,
                          height: 23,
                        ))
                  ],
                )
              ]),
            ],
          ),
        ),
        Divider(
          color: grey,
          height: 1,
          thickness: 1,
        )
      ],
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(0);
}
