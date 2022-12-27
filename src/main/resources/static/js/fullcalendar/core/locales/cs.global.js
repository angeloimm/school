/*!
FullCalendar Core v6.0.1
Docs & License: https://fullcalendar.io
(c) 2022 Adam Shaw
*/
(function (index_js) {
    'use strict';

    var locale = {
        code: 'cs',
        week: {
            dow: 1,
            doy: 4, // The week that contains Jan 4th is the first week of the year.
        },
        buttonText: {
            prev: 'Dříve',
            next: 'Později',
            today: 'Nyní',
            month: 'Měsíc',
            week: 'Týden',
            day: 'Den',
            list: 'Agenda',
        },
        weekText: 'Týd',
        allDayText: 'Celý den',
        moreLinkText(n) {
            return '+další: ' + n;
        },
        noEventsText: 'Žádné akce k zobrazení',
    };

    index_js.globalLocales.push(locale);

})(FullCalendar);
