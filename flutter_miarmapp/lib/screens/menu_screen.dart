import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/pages/home_page.dart';
import 'package:flutter_miarmapp/screens/profile_screen.dart';
import 'package:flutter_miarmapp/screens/search_screen.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_svg/flutter_svg.dart';

class MenuScreen extends StatefulWidget {
  const MenuScreen({Key? key}) : super(key: key);

  @override
  _MenuScreenState createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {
  int _currentIndex = 0;

  List<Widget> pages = [
    HomePageBloc(),
    const SearchScreen(),
    const SearchScreen(),
    const SearchScreen(),
    const ProfileScreen()
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
            margin: MediaQuery.of(context).padding,
            child: pages[_currentIndex]),
        bottomNavigationBar: _buildBottomBar());
  }

  Widget _buildBottomBar() {
    return Container(
        decoration: const BoxDecoration(
            border: Border(
          top: BorderSide(
            color: Color(0xfff1f1f1),
            width: 1.0,
          ),
        )),
        padding: const EdgeInsets.symmetric(horizontal: 20.0),
        height: 70,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            GestureDetector(
              child: SvgPicture.asset(
                'assets/icons/Home.svg',
                color: _currentIndex == 0 ? Colors.black87 : grey,
              ),
              onTap: () {
                setState(() {
                  _currentIndex = 0;
                });
              },
            ),
            GestureDetector(
              child: SvgPicture.asset(
                'assets/icons/Search.svg',
                color: _currentIndex == 1 ? Colors.black87 : grey,
              ),
              onTap: () {
                setState(() {
                  _currentIndex = 1;
                });
              },
            ),
            GestureDetector(
              child: SvgPicture.asset(
                'assets/icons/Reels.svg',
                color: _currentIndex == 2 ? Colors.black87 : grey,
              ),
              onTap: () {
                setState(() {
                  _currentIndex = 2;
                });
              },
            ),
            GestureDetector(
              child: SvgPicture.asset(
                'assets/icons/Shop.svg',
                color: _currentIndex == 3 ? Colors.black87 : grey,
              ),
              onTap: () {
                setState(() {
                  _currentIndex = 3;
                });
              },
            ),
            GestureDetector(
              onTap: () {
                setState(() {
                  _currentIndex = 4;
                });
              },
              child: Container(
                width: 30,
                height: 30,
                padding: const EdgeInsets.all(1),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(100),
                    border: Border.all(
                        color: _currentIndex == 4
                            ? Colors.black
                            : Colors.transparent,
                        width: 1.2)),
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(100),
                  child: Image.asset(''),
                ),
              ),
            )
          ],
        ));
  }
}
