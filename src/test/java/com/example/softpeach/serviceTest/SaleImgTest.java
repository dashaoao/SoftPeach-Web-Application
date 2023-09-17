package com.example.softpeach.serviceTest;

import com.example.softpeach.models.SaleImg;
import com.example.softpeach.repositories.SaleRepository;
import com.example.softpeach.services.SaleImgService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaleImgTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleImgService saleImgService;

    public SaleImgTest() {
        MockitoAnnotations.openMocks(this);
        saleImgService = new SaleImgService(saleRepository);
    }

    @Test
    public void testListSales() {
        List<SaleImg> saleImgList = new ArrayList<>();
        saleImgList.add(new SaleImg());
        when(saleRepository.findAll()).thenReturn(saleImgList);

        List<SaleImg> result = saleImgService.listSales();

        assertEquals(1, result.size());
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    public void testSaveSale() throws IOException {
        SaleImg saleImg = new SaleImg();
        saleImg.setName("Test Sale");

        saleImgService.saveSale(saleImg);

        verify(saleRepository, times(1)).existsByName("Test Sale");
        verify(saleRepository, times(1)).save(saleImg);
    }

    @Test
    public void testDeleteSale() {
        Long id = 1L;

        saleImgService.deleteSale(id);

        verify(saleRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetSaleById() {
        Long id = 1L;
        SaleImg saleImg = new SaleImg();
        when(saleRepository.findById(id)).thenReturn(java.util.Optional.of(saleImg));

        SaleImg result = saleImgService.getProductById(id);

        assertEquals(saleImg, result);
        verify(saleRepository, times(1)).findById(id);
    }
}