package com.taller.erjpa;

import com.taller.erjpa.service.TallerRelacionesService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TallerConsolaRunnerUnitTest {

    @Test
    void ejecutarComando_listarComite_invokesServiceAndPrints() throws Exception {
        TallerRelacionesService servicio = mock(TallerRelacionesService.class);
        when(servicio.listarMiembrosComite()).thenReturn("Miembros del comite\n- Ana Perez\n");

        TallerConsolaRunner runner = new TallerConsolaRunner(servicio);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream originalOut = System.out;

        try {
            System.setOut(ps);

            Method m = TallerConsolaRunner.class.getDeclaredMethod("ejecutarComando", String.class);
            m.setAccessible(true);
            m.invoke(runner, "listar-comite");

            ps.flush();
            String output = baos.toString();
            assertTrue(output.contains("Miembros del comite"));

            verify(servicio).listarMiembrosComite();
        } finally {
            System.setOut(originalOut);
        }
    }
}
