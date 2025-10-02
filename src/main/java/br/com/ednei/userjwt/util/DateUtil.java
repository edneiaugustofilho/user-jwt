package br.com.ednei.userjwt.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("unused")
public class DateUtil {

    public static final Locale localeDefault = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final SimpleDateFormat mesAnoFormat = new SimpleDateFormat("MM/yyyy", localeDefault);
    private static final SimpleDateFormat siglaMesAnoFormat = new SimpleDateFormat("MMM yyyy", localeDefault);

    public static LocalDate hoje() {
        return LocalDate.now();
    }

    public static LocalDate hojeAmericaSaoPaulo() {
        return LocalDate.now();
    }

    public static LocalDateTime agora() {
        return agora(ZoneId.of("UTC-3"));
    }

    public static LocalDateTime agora(ZoneId timeZone) {
        return LocalDateTime.now().atZone(timeZone).toLocalDateTime();
    }

    public static String getDia(LocalDate data) {
        String dia = "";

        if (!Objects.isNull(data)) {
            dia = String.valueOf(data.getDayOfMonth());
        }

        return dia;
    }

    public static Integer getMes(LocalDate data) {
        Integer mes = null;

        if (!Objects.isNull(data)) {
            mes = data.getMonth().getValue();
        }

        return mes;
    }

    public static Integer getMes() {
        return LocalDate.now().getMonth().getValue();
    }

    public static String getMesEAno(LocalDate data) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("America/Sao_Paulo"));

        String mes = zonedDateTime.getMonth().name();
        int ano = zonedDateTime.getYear();
        
        return mes + " de " + ano;
    }

    public static String getSemestre(LocalDate data) {
        if (data.getMonthValue() <= 6) {
            return "1";
        } else {
            return "2";
        }
    }

    public static int getAno(LocalDate data) {
        return data.getYear();
    }

    public static int getAno() {
        return LocalDate.now().getYear();
    }

    public static LocalDateTime addMinutos(LocalDateTime date, int minutos) {
        return date.plusMinutes(minutos);
    }

    public static int addAno(LocalDate date, int qntd) {
        return date.getYear() + qntd;
    }

    public static LocalDate addMes(LocalDate date, int qntd) {
        return date.plusMonths(qntd);
    }

    public static String formataDataHora(LocalDateTime date) {
        return formataDataHora(date, DateFormat.SHORT, DateFormat.SHORT);
    }

    public static String formataDataHora(LocalDateTime date, int dateStyle, int timeStyle) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, localeDefault);
        return Objects.nonNull(date) ? dateFormat.format(date) : "";
    }

    public static String formataData(LocalDate date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formataData(LocalDate date) {
        return formataData(date, DateFormat.SHORT);
    }

    public static String formataData(LocalDate date, int style) {
        String valorFormatado = "";

        if (!Objects.isNull(date)) {
            DateFormat dateFormat = DateFormat.getDateInstance(style, localeDefault);
            valorFormatado = dateFormat.format(date);
        }

        return valorFormatado;
    }

    public static String formatarHora(LocalDateTime date) {
        return formatarHora(date, DateFormat.SHORT);
    }

    public static String formatarHora(LocalDateTime date, int style) {
        String valorFormatado = "";

        if (!Objects.isNull(date)) {
            DateFormat dateFormat = DateFormat.getTimeInstance(style, localeDefault);
            valorFormatado = dateFormat.format(date);
        }

        return valorFormatado;
    }

    public static Integer getHoraInteiroDeData(LocalDateTime date) {
        Integer valor = null;

        String formatarHora = formatarHora(date);
        if (Objects.nonNull(formatarHora)) {
            valor = Integer.parseInt(formatarHora.replace(":", ""));
        }

        return valor;
    }

    public static Integer getHoraMinutoSegundoInteiroDeData(LocalDateTime date) {
        Integer valor = null;

        String formatarHora = formatarHora(date, DateFormat.LONG);
        if (Objects.nonNull(formatarHora)) {
            valor = Integer.parseInt(formatarHora.replace(":", ""));
        }

        return valor;
    }

    public static String getNomeMesDaData(LocalDate date) {
        return getNomeMesDaData(date, false);
    }

    public static String getNomeMesDaData(LocalDate date, boolean abreviado) {
        if (Objects.nonNull(date)) {
            int intMes = date.getMonthValue();

            return extrairNomeMes(intMes, abreviado);
        }

        return null;
    }

    public static String getNomeMes(int indexMes) {
        return getNomeMes(indexMes, false);
    }

    public static String getNomeMes(int indexMes, boolean abreviado) {
        return extrairNomeMes(indexMes, abreviado);
    }

    private static String extrairNomeMes(int indexMes, boolean abreviado) {
        return switch (indexMes) {
            case 1 -> (abreviado ? "jan" : "janeiro");
            case 2 -> (abreviado ? "fev" : "fevereiro");
            case 3 -> (abreviado ? "mar" : "marco");
            case 4 -> (abreviado ? "abr" : "abril");
            case 5 -> (abreviado ? "mai" : "maio");
            case 6 -> (abreviado ? "jun" : "junho");
            case 7 -> (abreviado ? "jul" : "julho");
            case 8 -> (abreviado ? "ago" : "agosto");
            case 9 -> (abreviado ? "set" : "setembro");
            case 10 -> (abreviado ? "out" : "outubro");
            case 11 -> (abreviado ? "nov" : "novembro");
            case 12 -> (abreviado ? "dez" : "dezembro");
            default -> "";
        };
    }

    public static String getNomeDiaSemana(LocalDate data) {
        return getNomeDiaSemana(data, false);
    }

    public static String getNomeDiaSemana(LocalDate data, Boolean abreviado) {
        String diaSemana = "";

        if (!Objects.isNull(data)) {
            return switch (data.getDayOfWeek()) {
                case MONDAY -> (abreviado ? "seg" : "segunda");
                case TUESDAY -> (abreviado ? "ter" : "terça");
                case WEDNESDAY -> (abreviado ? "qua" : "quarta");
                case THURSDAY -> (abreviado ? "qui" : "quinta");
                case FRIDAY -> (abreviado ? "sex" : "sexta");
                case SATURDAY -> (abreviado ? "sab" : "sabado");
                case SUNDAY -> (abreviado ? "dom" : "domingo");
            };
        }

        return diaSemana;
    }

    public static LocalDateTime ultimaHoraData(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 23, 59, 59);
    }

    public static LocalDateTime primeiraHoraData(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0, 0);
    }

    public static LocalDateTime primeiroDiaData(LocalDateTime date) {
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), 1, 0, 0, 0);
    }

    public static LocalDate ultimoDiaData(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonthValue(), date.lengthOfMonth());
    }

    public static LocalDateTime zerarHoraData(LocalDateTime date) {
        return primeiraHoraData(date);
    }

    public static LocalTime zerarHora() {
        return LocalTime.of(0, 0, 0);
    }

    public static LocalTime ultimaHora() {
        return LocalTime.of(23, 59, 59);
    }

    public static Integer getDiasEntreDatas(LocalDate dataInicial, LocalDate dataFinal) {
        return Math.toIntExact(ChronoUnit.DAYS.between(dataInicial, dataFinal));
    }

    public static LocalDate somarDiasAData(LocalDate data, int dias) {
        return data.plusDays(dias);
    }

    public static int getDiferencaHorasEntreDuasDatas(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return Math.toIntExact(ChronoUnit.HOURS.between(dateStart, dateEnd));
    }

    public static int getDiferencaMinutosEntreDuasDatas(LocalDateTime dateStart, LocalDateTime dateEnd) {
        return Math.toIntExact(ChronoUnit.MINUTES.between(dateStart, dateEnd));
    }

    public static LocalDate converterDateStringParaDate(String data) throws ParseException {
        return converterDateStringParaDate(data, DATE_FORMAT);
    }

    public static LocalDate converterDateStringParaDate(String data, String dateFormat) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(data).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String converterDataForStringFormatoMMDDyyyy_hhmmss(LocalDate data) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
        return formatter.format(data);
    }


    public static String getSiglaMesAno(LocalDate date) {
        String mesAno = "";

        if (Objects.nonNull(date)) {
            mesAno = siglaMesAnoFormat.format(date).toUpperCase();
        }

        return mesAno;
    }

    public static boolean isFinalDeSemana(LocalDateTime localDateTime) {
        return isFinalDeSemana(localDateTime.toLocalDate());
    }

    public static boolean isFinalDeSemana(LocalDate localDate) {
        List<DayOfWeek> finaisDeSemana = new ArrayList<>();
        finaisDeSemana.add(DayOfWeek.FRIDAY);
        finaisDeSemana.add(DayOfWeek.SATURDAY);
        finaisDeSemana.add(DayOfWeek.SUNDAY);

        return finaisDeSemana.contains(localDate.getDayOfWeek());
    }

    public static boolean isDiaUtil(LocalDate localDate) {
        boolean isSabado = localDate.getDayOfWeek() == DayOfWeek.SATURDAY;
        boolean isDomingo = localDate.getDayOfWeek() == DayOfWeek.SUNDAY;

        return !(isSabado || isDomingo);
    }

    public static boolean isDiaSemana(DayOfWeek dayOfWeek) {
        return LocalDate.now().getDayOfWeek() == dayOfWeek;
    }

    public static boolean isSegundaFeira() {
        return isDiaSemana(DayOfWeek.MONDAY);
    }

    public static boolean isTercaFeira() {
        return isDiaSemana(DayOfWeek.TUESDAY);
    }

    public static boolean isQuartaFeira() {
        return isDiaSemana(DayOfWeek.WEDNESDAY);
    }

    public static boolean isQuintaFeira() {
        return isDiaSemana(DayOfWeek.THURSDAY);
    }

    public static boolean isSextaFeira() {
        return isDiaSemana(DayOfWeek.FRIDAY);
    }

    public static boolean isSabado() {
        return isDiaSemana(DayOfWeek.SATURDAY);
    }

    public static boolean isDomingo() {
        return isDiaSemana(DayOfWeek.SUNDAY);
    }

    public static boolean isHoraAtualMaiorOuIgualA(int hora, int minuto) {
        return LocalDateTime.now().toLocalTime().isAfter(LocalTime.of(hora, minuto)) ||
                LocalDateTime.now().toLocalTime().equals(LocalTime.of(hora, minuto));
    }

    public static boolean isHoraAtualMenorOuIgualA(int hora, int minuto) {
        return LocalDateTime.now().toLocalTime().isBefore(LocalTime.of(hora, minuto)) ||
                LocalDateTime.now().toLocalTime().equals(LocalTime.of(hora, minuto));
    }

    public static LocalDate maisDiasUteis(LocalDate localDate, int qtde) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        int qtdeDiasUteisSomados = 0;
        while (qtdeDiasUteisSomados < qtde) {
            localDate = localDate.plusDays(1);
            if (DateUtil.isDiaUtil(localDate)) {
                qtdeDiasUteisSomados++;
            }
        }

        return localDate;
    }

    public static LocalDate menosDiasUteis(LocalDate localDate, int qtde) {
        if (Objects.isNull(localDate)) {
            return null;
        }

        int qtdeDiasUteisSomados = 0;
        while (qtdeDiasUteisSomados < qtde) {
            localDate = localDate.minusDays(1);
            if (DateUtil.isDiaUtil(localDate)) {
                qtdeDiasUteisSomados++;
            }
        }

        return localDate;
    }
}
