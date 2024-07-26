function inactiveDefaultButton() {
    $('form').off('keypress.disableAutoSubmitOnEnter').on(
        'keypress.disableAutoSubmitOnEnter',
        function (event) {
            if (event.which === $.ui.keyCode.ENTER
                && $(event.target).is(
                    ':input:not(textarea,:button,:submit,:reset)')) {
                event.preventDefault();
            }
        });
}

function changeRightPanelDisplayState() {
    var rightPanel = document.getElementsByClassName("layout-rightpanel");

    if (rightPanel.length > 0) {
        if (rightPanel[0].classList.contains('layout-rightpanel-active')) {
            rightPanel[0].className = rightPanel[0].className.replace(' layout-rightpanel-active', '');
        } else {
            rightPanel[0].className = rightPanel[0].className + ' layout-rightpanel-active';
            updateOnNeededRightPanel();
        }
    }
}

function blockDialog(blockUiName) {
    if (blockUiName)
        PF(blockUiName).show();
    else
        PF('crudDialogBui').show();
}

function unblockDialog(blockUiName) {
    if (blockUiName)
        PF(blockUiName).hide();
    else
        PF('crudDialogBui').hide();
}

function showBlockPage() {
    showDialog('blockPage');
}

function hideBlockPage() {
    hideDialog('blockPage');
}

function showDialog(dialogWidgetVar) {
    PF(dialogWidgetVar).show();
}

function hideDialog(dialogWidgetVar) {
    PF(dialogWidgetVar).hide();
}

PrimeFaces.locales['fr'] =
    {
        monthNames: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin',
            'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
        monthNamesShort: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Jun', 'Jul', 'Aoû',
            'Sep', 'Oct', 'Nov', 'Déc'],
        dayNames: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi',
            'Samedi'],
        dayNamesShort: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
        dayNamesLong: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi',
            'Vendredi', 'Samedi'],
        dayNamesMin: ['D', 'L', 'M', 'M', 'J', 'V', 'S'],

        firstDay: 1,
        firstDayOfWeek: 1,
        isRTL: false,
        showMonthAfterYear: false,
        ampm: false,
        allDayText: '',
        ShowAll: 'Tous'
    };