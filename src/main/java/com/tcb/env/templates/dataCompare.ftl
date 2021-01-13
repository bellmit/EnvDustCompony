<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2006-09-16T00:00:00Z</Created>
  <LastSaved>2018-05-28T01:02:27Z</LastSaved>
  <Version>15.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>8010</WindowHeight>
  <WindowWidth>14810</WindowWidth>
  <WindowTopX>240</WindowTopX>
  <WindowTopY>110</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s62">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom" ss:ShrinkToFit="1"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s63">
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s65">
   <Alignment ss:Horizontal="Center" ss:Vertical="Bottom"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Interior ss:Color="#BFBFBF" ss:Pattern="Solid"/>
   <NumberFormat ss:Format="@"/>
  </Style>
  <Style ss:ID="s66">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="11" ss:Color="#000000"
    ss:Bold="1"/>
   <NumberFormat ss:Format="@"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="统计报表">
  <Table ss:ExpandedColumnCount="5" x:FullColumns="1"
   x:FullRows="1" ss:DefaultRowHeight="14">
   <Column ss:Index="2" ss:Width="169.5"/>
   <Column ss:Width="157.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="73"/>
   <Column ss:AutoFitWidth="0" ss:Width="80.5"/>
   <Row>
    <Cell ss:Index="2" ss:MergeAcross="3" ss:MergeDown="1" ss:StyleID="s66"><Data
      ss:Type="String">${compareTitle!}</Data></Cell>
   </Row>
   <Row ss:Index="3">
    <Cell ss:Index="2" ss:StyleID="s63"/>
    <Cell ss:StyleID="s63"/>
    <Cell ss:StyleID="s63"><Data ss:Type="String">导出时间：</Data></Cell>
    <Cell ss:StyleID="s63"><Data ss:Type="String">${outTime!}</Data></Cell>
   </Row>
   <Row>
    <Cell ss:Index="2" ss:StyleID="s65"><Data ss:Type="String">所属区域</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">设备名称</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">${originalTime!}</Data></Cell>
    <Cell ss:StyleID="s65"><Data ss:Type="String">${compareTime!}</Data></Cell>
   </Row>
   	<#if (dataCompareList?size>0) >
		<#list dataCompareList as itemValue>
		   <Row>
			<Cell ss:Index="2" ss:StyleID="s62"><Data ss:Type="String">${itemValue.areaName!}</Data></Cell>
			<Cell ss:StyleID="s62"><Data ss:Type="String">${itemValue.deviceName!}</Data></Cell>
			<Cell ss:StyleID="s62"><Data ss:Type="String">${itemValue.originalValue!}</Data></Cell>
			<Cell ss:StyleID="s62"><Data ss:Type="String">${itemValue.compareValue!}</Data></Cell>
		   </Row>
   	   </#list>
	</#if>
   <Row ss:AutoFitHeight="0">
    <Cell ss:Index="2" ss:StyleID="s62"><Data ss:Type="String"></Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String"></Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String"></Data></Cell>
    <Cell ss:StyleID="s62"><Data ss:Type="String"></Data></Cell>
   </Row>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>-3</HorizontalResolution>
    <VerticalResolution>0</VerticalResolution>
   </Print>
   <Selected/>
   <FreezePanes/>
   <FrozenNoSplit/>
   <SplitHorizontal>4</SplitHorizontal>
   <TopRowBottomPane>4</TopRowBottomPane>
   <ActivePane>2</ActivePane>
   <Panes>
    <Pane>
     <Number>3</Number>
    </Pane>
    <Pane>
     <Number>2</Number>
     <ActiveRow>1</ActiveRow>
     <ActiveCol>7</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
