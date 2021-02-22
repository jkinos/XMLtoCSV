package fi.academicwork.xmltocsv;

import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class ObjectParser {

    public List<String[]> parseInvoiceObject(Finvoice invoice) throws Exception {

        // parsing properties from invoice java object

        BuyerPartyDetails buyerPartyDetails = invoice.getBuyerPartyDetails();
        String businessID = buyerPartyDetails.getBuyerPartyIdentifier();
        List<String> buyerOrganisationNameList = buyerPartyDetails.getBuyerOrganisationName();
        String nameOfBusinessPartner = "";
        for (String buyerOrganisationName : buyerOrganisationNameList) {
            nameOfBusinessPartner = String.join("", nameOfBusinessPartner, buyerOrganisationName);
        }
        BuyerPostalAddressDetails buyerAddress = buyerPartyDetails.getBuyerPostalAddressDetails();
        List<String> buyerStreetNameList = buyerAddress.getBuyerStreetName();
        String buyerStreet = "";
        for (String buyerStreetName : buyerStreetNameList) {
            buyerStreet = String.join("", buyerStreet, buyerStreetName);
        }
        String buyerTownName = buyerAddress.getBuyerTownName();
        String buyerPostCodeIdentifier = buyerAddress.getBuyerPostCodeIdentifier();
        String buyerCountryCode = buyerAddress.getCountryCode();
        String addressOfBusinessPartner = String.join("\\", nameOfBusinessPartner, buyerStreet, buyerPostCodeIdentifier,
                buyerTownName, buyerCountryCode);

        DeliveryPartyDetails deliveryPartyDetails = invoice.getDeliveryPartyDetails();
        List<String> deliveryOrganisationNameList = deliveryPartyDetails.getDeliveryOrganisationName();
        String deliveryOrganisation = "";
        for (String deliveryOrganisationName : deliveryOrganisationNameList) {
            deliveryOrganisation = String.join("", deliveryOrganisation, deliveryOrganisationName);

        }
        DeliveryPostalAddressDetails deliveryPostalAddressDetails = deliveryPartyDetails
                .getDeliveryPostalAddressDetails();
        List<String> deliveryStreetNameList = deliveryPostalAddressDetails.getDeliveryStreetName();
        String deliveryStreet = "";
        for (String deliveryStreetName : deliveryStreetNameList) {
            deliveryStreet = String.join("", deliveryStreet, deliveryStreetName);
        }
        String deliveryTownName = deliveryPostalAddressDetails.getDeliveryTownName();
        String deliveryPostCodeIdentifier = deliveryPostalAddressDetails.getDeliveryPostCodeIdentifier();
        String deliveryCountryCode = deliveryPostalAddressDetails.getCountryCode();
        String deliveryAddress = String.join("\\", deliveryOrganisation, deliveryStreet, deliveryPostCodeIdentifier,
                deliveryTownName, deliveryCountryCode);

        InvoiceDetails invoiceDetails = invoice.getInvoiceDetails();
        String invoiceType = invoiceDetails.getInvoiceTypeCode().getValue();

        Date invoiceDate = invoiceDetails.getInvoiceDate();
        String dateValue = invoiceDate.getValue().toString();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd.M.yyy");
        String strInvoiceDate = formatter2.format(formatter1.parse(dateValue));

        String currencyCode = invoiceDetails.getInvoiceTotalVatExcludedAmount().getAmountCurrencyIdentifier();
        String penalInterest = invoiceDetails.getPaymentTermsDetails().get(0).getPaymentOverDueFineDetails()
                .getPaymentOverDueFinePercent();
        List<String> freeText = invoiceDetails.getInvoiceFreeText();
        String additionalInvoiceInfo = "";
        for (String text : freeText) {
            additionalInvoiceInfo = String.join("", additionalInvoiceInfo, text);
        }

        // first line for csv
        String invoiceLine1[] = { invoiceType, currencyCode, "", "", businessID, "", nameOfBusinessPartner, "", "", "",
                "", penalInterest, strInvoiceDate, "", "", "", addressOfBusinessPartner, deliveryAddress,
                additionalInvoiceInfo };

        List<InvoiceRow> invoiceRows = invoice.getInvoiceRow();
        List<String[]> invoiceArticleRowLines = new ArrayList<String[]>();

        for (int i = 0; i < invoiceRows.size(); i++) {
            String articleIdentifier = invoiceRows.get(i).getArticleIdentifier();
            String articleName = invoiceRows.get(i).getArticleName();
            String orderedQuantity = invoiceRows.get(i).getOrderedQuantity().getValue();
            UnitAmount unitAmount = invoiceRows.get(i).getUnitPriceAmount();
            String unitPriceAmount = unitAmount.getValue();
            String UnitPriceUnitCode = unitAmount.getUnitPriceUnitCode();
            String rowVatRatePercent = invoiceRows.get(i).getRowVatRatePercent();

            // articleRowLines for csv
            String invoiceRowLine[] = { "", articleName, articleIdentifier, orderedQuantity, UnitPriceUnitCode,
                    unitPriceAmount, "", rowVatRatePercent };
            invoiceArticleRowLines.add(invoiceRowLine);
        }

        List<String[]> csvRows = new ArrayList<>();
        csvRows.add(invoiceLine1);
        csvRows.addAll(invoiceArticleRowLines);

        return csvRows;

    }
}
