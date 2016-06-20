package no.rutebanken.tiamat.dtoassembling.assembler;


import no.rutebanken.tiamat.dtoassembling.dto.QuayDto;
import org.junit.Test;
import no.rutebanken.tiamat.model.Quay;
import no.rutebanken.tiamat.model.QuayTypeEnumeration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

public class QuayAssemblerTest {

    private QuayAssembler quayAssembler = new QuayAssembler(mock(SimplePointAssembler.class));

    @Test
    public void assembleQuayWithQuayType() {


        Quay quay = new Quay();
        quay.setQuayType(QuayTypeEnumeration.BUS_BAY);

        QuayDto quayDto = quayAssembler.assemble(quay);


        assertThat(quayDto.quayType).isEqualTo("busBay");
    }

}