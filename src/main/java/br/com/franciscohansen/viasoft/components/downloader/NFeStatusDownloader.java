package br.com.franciscohansen.viasoft.components.downloader;

import br.com.franciscohansen.viasoft.enums.EEstado;
import br.com.franciscohansen.viasoft.model.StatusUF;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NFeStatusDownloader {
    private static final String STATUS_URL = "http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx";
    private static final String TABLE_ID = "ctl00_ContentPlaceHolder1_gdvDisponibilidade2";
    private static final String BOLA_VERDE = "imagens/bola_verde_P.png";
    private static final String BOLA_AMARELA = "imagens/bola_amarela_P.png";
    private static final String BOLA_VERMELHA = "imagens/bola_vermelho_P.png";

    private final Document document;

    public NFeStatusDownloader() throws IOException {
        this.document = Jsoup.connect(STATUS_URL).get();
    }

    public List<StatusUF> read() { //Ler documento
        List<StatusUF> lista = new ArrayList<>();
        Element table = this.document.getElementById(TABLE_ID);
        Element tbody = table.child(1);
        List<Element> lines = readLine(tbody);
        for (Element line : lines) {
            StatusUF status = readStatusLine(line);
            if (status != null) {
                lista.add(status);
            }
        }
        return lista;
    }

    private List<Element> readLine(Element elem) {
        List<Element> lines = new ArrayList<>();
        for (int i = 1; i < elem.childNodeSize() - 1; i++) { //Começa de 1 e termina em length -1 para pular primeiro e último elementos
            lines.add(elem.child(i));
        }
        return lines;
    }

    private List<Element> readChildren(Element elem) {
        List<Element> lines = new ArrayList<>();
        for (int i = 0; i < elem.childrenSize(); i++) { //Começa de 1 e termina em length -1 para pular primeiro e último elementos
            lines.add(elem.child(i));
        }
        return lines;
    }

    private EEstado readStatus(Element cell) {
        if (cell.childNode(0).nodeName().equalsIgnoreCase("img")) {
            switch (cell.childNode(0).attr("src")) {
                case BOLA_VERDE:
                    return EEstado.VERDE;
                case BOLA_AMARELA:
                    return EEstado.AMARELO;
                case BOLA_VERMELHA:
                    return EEstado.VERMELHO;
                default:
                    return EEstado.NONE;
            }
        }
        return EEstado.NONE;
    }

    private StatusUF readStatusLine(Element line) {
        List<Element> cells = readChildren(line);
        if (!cells.isEmpty()) {
            return StatusUF.builder()
                    .autorizador(cells.get(0).childNode(0).toString())
                    .data(Calendar.getInstance().getTime())
                    .autorizacao(readStatus(cells.get(1)))
                    .retAutorizacao(readStatus(cells.get(2)))
                    .inutilizacao(readStatus(cells.get(3)))
                    .consultaProtocolo(readStatus(cells.get(4)))
                    .statusServico(readStatus(cells.get(5)))
                    .consultaCadastro(readStatus(cells.get(7)))
                    .recepcaoEvento(readStatus(cells.get(8)))
                    .build();
        } else {
            return null;
        }
    }


}
