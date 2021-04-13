import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_gen/gen_l10n/age-localizations.dart';
import 'package:auto_size_text/auto_size_text.dart';
import 'package:time_machine/time_machine.dart';

void main() {
  runApp(MyApp());
}

/*
* Use this for the default arb file generation
// flutter pub run intl_translation:extract_to_arb --output-dir=lib/l10n lib/main.dart
class AgeLocalizations {
  static const supportedLanguages = ['en', 'de', 'fr', 'es'];
  final String localeName;

  AgeLocalizations(this.localeName);

  static Future<AgeLocalizations> load(Locale locale) {
    final name =
        locale.countryCode == null ? locale.languageCode : locale.toString();
    final localeName = Intl.canonicalizedLocale(name);

    return initializeMessages(localeName).then((_) {
      return AgeLocalizations(localeName);
    });
  }

  static AgeLocalizations? of(BuildContext context) {
    return Localizations.of<AgeLocalizations>(context, AgeLocalizations);
  }

  String age(String specifier) {
    return Intl.message('Java 8 is $specifier old today.',
        name: 'age', args: [specifier]);
  }

  String get loading => Intl.message('very', name: 'loading');

  String get and => Intl.message('and', name: 'and');

  String timeYear(int years) => Intl.plural(years,
      other: "$years years", one: 'one year', args: [years], name: 'timeYear');

  String timeMonths(int months) => Intl.plural(months,
      other: "$months months",
      one: 'one month',
      args: [months],
      name: 'timeMonths');

  String timeDays(int days) => Intl.plural(days,
      other: "$days days", one: 'one day', args: [days], name: 'timeDays');
}

class AgeLocalizationsDelegate extends LocalizationsDelegate<AgeLocalizations> {
  const AgeLocalizationsDelegate();

  @override
  bool isSupported(Locale locale) =>
      AgeLocalizations.supportedLanguages.contains(locale);

  @override
  Future<AgeLocalizations> load(Locale locale) => AgeLocalizations.load(locale);

  @override
  bool shouldReload(covariant LocalizationsDelegate<AgeLocalizations> old) =>
      false;
}*/

class Palette {
  static const Color backgroundDark = Color(0xff121212);
  static const Color backgroundLight = Color(0xffffffff);
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'howoldisjava8',
      localizationsDelegates: AppLocalizations.localizationsDelegates,
      supportedLocales: AppLocalizations.supportedLocales,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        canvasColor: Palette.backgroundLight,
        primaryColor: Palette.backgroundLight,
        brightness: Brightness.light,
        appBarTheme: AppBarTheme(elevation: 0),
      ),
      darkTheme: ThemeData(
        canvasColor: Palette.backgroundDark,
        primaryColor: Palette.backgroundDark,
        brightness: Brightness.dark,
        appBarTheme: AppBarTheme(elevation: 0),
      ),
      home: MyHomePage(title: 'How old is Java 8 today?'),
      debugShowCheckedModeBanner: false,
      color: Palette.backgroundDark,
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _dateSpecifier(Period period) {
    return "${AppLocalizations.of(context)?.timeYear(period.years)}, ${AppLocalizations.of(context)?.timeMonths(period.months)} ${AppLocalizations.of(context)?.and} ${AppLocalizations.of(context)?.timeDays(period.days)}";
  }

  String? get age {
    return AppLocalizations.of(context)
        ?.age(_dateSpecifier(periodSinceJavaRelease));
  }

  Period get periodSinceJavaRelease {
    const int unixJava8Release = 1395097200000;
    return Period.differenceBetweenDateTime(
        LocalDateTime.dateTime(
            DateTime.fromMillisecondsSinceEpoch(unixJava8Release)),
        LocalDateTime.dateTime(DateTime.now()));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
              child: AutoSizeText(
                age ?? "",
                maxLines: 5,
                style: TextStyle(
                  fontSize: 1000,
                ),
                textAlign: TextAlign.center,
              ),
            ),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
