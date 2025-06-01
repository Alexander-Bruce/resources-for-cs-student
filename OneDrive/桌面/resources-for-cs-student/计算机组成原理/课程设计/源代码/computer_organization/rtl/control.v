/*组合逻辑控制单元，根据时钟生成为控制信号和内部信号*/
/*
输入：
       din：指令，8位，来自IR；
       clk：时钟信号，1位，上升沿有效；
       rst：复位信号，1位，与cpustate共同组成reset信号；
       cpustate：当前CPU的状态（IN输入01，CHECK检查10，RUN运行11），2位；
       z：零标志，1位，零标志寄存器的输出，如果指令中涉及到z，可加上，否则可去掉；
输出：
      clr：清零控制信号
     自行设计的各个控制信号
*/
//省略号中是自行设计的控制信号，需要自行补充，没用到z的话去掉z

module control(din,clk,rst,z,cpustate,clr,	arload,arinc,pcload,pcinc,pcbus,drload,drhbus,drlbus,irload,trload,trbus,
					r0load,r0bus,r1load,r1bus,r2load,r2bus,r3load,r3bus,xload,alus,yload,ybus,zload,read,write,membus,busmem); 
input [7:0]din;
input clk;
input rst,z;
input [1:0] cpustate;
//补充输出端口说明
//parameter's define
wire arload,arinc,pcload,pcinc,pcbus,drload,drhbus,drlbus,irload,trload,trbus,
		r0load,r0bus,r1load,r1bus,r2load,r2bus,r3load,r3bus,xload,yload,ybus,zload,read,write,membus,busmem;
output clr,arload,arinc,pcload,pcinc,pcbus,drload,drhbus,drlbus,irload,trload,trbus,
       r0load,r0bus,r1load,r1bus,r2load,r2bus,r3load,r3bus,xload,yload,ybus,zload,read,write,membus,busmem;	 
output reg [2:0] alus;
		 
wire reset;
//在下方加上自行定义的状态
//OK
wire fetch1,fetch2,fetch3,nop1,add1,add2,add3,sub1,sub2,sub3,and1,and2,and3,or1,or2,or3,inc1,inc2,dec1,dec2,not1,not2,shl1,shl2,mvr1,mvrd1,mvrd2,
		jmp1,jmp2,jmp3,jmpz1,jmpz2,jmpz3,jmpz4,jmpz5,jpnz1,jpnz2,jpnz3,jpnz4,jpnz5,lad1,lad2,lad3,lad4,lad5,sto1,sto2,sto3,sto4,sto5;
		
//加上自行设计的指令，这里是译码器的输出，所以nop指令经译码器输出后为inop。
//类似地，add指令指令经译码器输出后为iadd；inac指令经译码器输出后为iinac，......
//OK
reg inop,iadd,isub,iand,ior,iinc,idec,inot,ishl,imvr,imvrd,ijmp,ijmpz,ijpnz,ilad,isto;

//时钟节拍，8个为一个指令周期，t0-t2分别对应fetch1-fetch3，t3-t7分别对应各指令的执行周期，当然不是所有指令都需要5个节拍的。例如add指令只需要2个节拍：t3和t4
//OK
reg t0,t1,t2,t3,t4,t5,t6,t7; //时钟节拍，8个为一个周期

// 内部信号：clr清零，inc自增
//OK
wire clr;
wire inc;
assign reset = rst&(cpustate == 2'b11);
// assign signals for the cunter

//clr信号是每条指令执行完毕后必做的清零，下面clr赋值语句要修改，需要“或”各指令的最后一个周期
//OK
assign clr=nop1 || add3 || sub3 || and3 || or3 || inc2 || dec2 || not2 || shl2 || mvr1 || mvrd2 || jmp3 || jmpz5 || jpnz5 || lad5 || sto5;
assign inc=~clr;

//generate the control signal using state information
//...
//取公过程
assign fetch1=t0;
assign fetch2=t1;
assign fetch3=t2;
//什么都不做的译码
assign nop1=inop&&t3;//inop表示nop指令，nop1是nop指令的执行周期的第一个状态也是最后一个状态，因为只需要1个节拍t3完成

//以下写出各条指令状态的表达式
assign add1=iadd&&t3;	assign add2=iadd&&t4;	assign add3=iadd&&t5;
assign sub1=isub&&t3;	assign sub2=isub&&t4;	assign sub3=isub&&t5;
assign and1=iand&&t3;	assign and2=iand&&t4;	assign and3=iand&&t5;
assign or1=ior&&t3;		assign or2=ior&&t4;		assign or3=ior&&t5;
assign inc1=iinc&&t3;	assign inc2=iinc&&t4;
assign dec1=idec&&t3;	assign dec2=idec&&t4;
assign not1=inot&&t3;	assign not2=inot&&t4;
assign shl1=ishl&&t3;	assign shl2=ishl&&t4;
assign mvr1=imvr&&t3;
assign mvrd1=imvrd&&t3;	assign mvrd2=imvrd&&t4;
assign jmp1=ijmp&&t3;	assign jmp2=ijmp&&t4;	assign jmp3=ijmp&&t5;
assign jmpz1=ijmpz&&t3;	assign jmpz2=ijmpz&&t4;	assign jmpz3=ijmpz&&t5;	assign jmpz4=ijmpz&&t6;	assign jmpz5=ijmpz&&t7;
assign jpnz1=ijpnz&&t3;	assign jpnz2=ijpnz&&t4;	assign jpnz3=ijpnz&&t5;	assign jpnz4=ijpnz&&t6;	assign jpnz5=ijpnz&&t7;
assign lad1=ilad&&t3;	assign lad2=ilad&&t4;	assign lad3=ilad&&t5;	assign lad4=ilad&&t6;	assign lad5=ilad&&t7;
assign sto1=isto&&t3;	assign sto2=isto&&t4;	assign sto3=isto&&t5;	assign sto4=isto&&t6;	assign sto5=isto&&t7;

//以下给出了pcbus的逻辑表达式，写出其他控制信号的逻辑表达式
assign arload= fetch1 || fetch3 || lad3 || sto3;
assign arinc= jmp1 || jmpz1 || jpnz1 || lad1 || sto1;
assign pcload= jmp3 || jmpz3&&(z==1'b1) || jpnz3&&(z==1'b0);
assign pcinc= fetch2 || mvrd1 || jmpz4&&(z==1'b0) || jmpz5&&(z==1'b0) || jpnz4&&(z==1'b1) || jpnz5&&(z==1'b1) || lad1 || lad2 || sto1 || sto2;
assign pcbus= fetch1 || fetch3;
assign drload= fetch2 || mvrd1 || jmp1 || jmp2 || jmpz1 || jmpz2 || jpnz1 || jpnz2 || lad1 || lad2 || lad4 || sto1 || sto2 || sto4;
assign drhbus= jmp3 || jmpz3&&(z==1'b1) || jpnz3&&(z==1'b0) || lad3 || sto3;
assign drlbus= mvrd2 || lad5 || sto5;
assign irload= fetch3;
assign trload= jmp2 || jmpz2 || jpnz2 || lad2 || sto2;
assign trbus= jmp3 || jmpz3&&(z==1'b1) || jpnz3&&(z==1'b0) || lad3 || sto3;
assign r0load= add3&&(din[3:2]==2'b00) || sub3&&(din[3:2]==2'b00) || and3&&(din[3:2]==2'b00) || or3&&(din[3:2]==2'b00) || inc2&&(din[3:2]==2'b00) || dec2&&(din[3:2]==2'b00) || not2&&(din[3:2]==2'b00) || shl2&&(din[3:2]==2'b00) || mvr1&&(din[3:2]==2'b00) || mvrd2&&(din[3:2]==2'b00) || lad5&&(din[3:2]==2'b00);
assign r0bus= add1&&(din[1:0]==2'b00) || add2&&(din[3:2]==2'b00) || sub1&&(din[1:0]==2'b00) || sub2&&(din[3:2]==2'b00) || and1&&(din[1:0]==2'b00) || and2&&(din[3:2]==2'b00) || or1&&(din[1:0]==2'b00) || or2&&(din[3:2]==2'b00) || inc1&&(din[3:2]==2'b00) || dec1&&(din[3:2]==2'b00) || not1&&(din[3:2]==2'b00) || shl1&&(din[3:2]==2'b00) || mvr1&&(din[1:0]==2'b00) || sto4&&(din[3:2]==2'b00);
assign r1load= add3&&(din[3:2]==2'b01) || sub3&&(din[3:2]==2'b01) || and3&&(din[3:2]==2'b01) || or3&&(din[3:2]==2'b01) || inc2&&(din[3:2]==2'b01) || dec2&&(din[3:2]==2'b01) || not2&&(din[3:2]==2'b01) || shl2&&(din[3:2]==2'b01) || mvr1&&(din[3:2]==2'b01) || mvrd2&&(din[3:2]==2'b01) || lad5&&(din[3:2]==2'b01);
assign r1bus= add1&&(din[1:0]==2'b01) || add2&&(din[3:2]==2'b01) || sub1&&(din[1:0]==2'b01) || sub2&&(din[3:2]==2'b01) || and1&&(din[1:0]==2'b01) || and2&&(din[3:2]==2'b01) || or1&&(din[1:0]==2'b01) || or2&&(din[3:2]==2'b01) || inc1&&(din[3:2]==2'b01) || dec1&&(din[3:2]==2'b01) || not1&&(din[3:2]==2'b01) || shl1&&(din[3:2]==2'b01) || mvr1&&(din[1:0]==2'b01) || sto4&&(din[3:2]==2'b01);
assign r2load= add3&&(din[3:2]==2'b10) || sub3&&(din[3:2]==2'b10) || and3&&(din[3:2]==2'b10) || or3&&(din[3:2]==2'b10) || inc2&&(din[3:2]==2'b10) || dec2&&(din[3:2]==2'b10) || not2&&(din[3:2]==2'b10) || shl2&&(din[3:2]==2'b10) || mvr1&&(din[3:2]==2'b10) || mvrd2&&(din[3:2]==2'b10) || lad5&&(din[3:2]==2'b10);
assign r2bus= add1&&(din[1:0]==2'b10) || add2&&(din[3:2]==2'b10) || sub1&&(din[1:0]==2'b10) || sub2&&(din[3:2]==2'b10) || and1&&(din[1:0]==2'b10) || and2&&(din[3:2]==2'b10) || or1&&(din[1:0]==2'b10) || or2&&(din[3:2]==2'b10) || inc1&&(din[3:2]==2'b10) || dec1&&(din[3:2]==2'b10) || not1&&(din[3:2]==2'b10) || shl1&&(din[3:2]==2'b10) || mvr1&&(din[1:0]==2'b10) || sto4&&(din[3:2]==2'b10);
assign r3load= add3&&(din[3:2]==2'b11) || sub3&&(din[3:2]==2'b11) || and3&&(din[3:2]==2'b11) || or3&&(din[3:2]==2'b11) || inc2&&(din[3:2]==2'b11) || dec2&&(din[3:2]==2'b11) || not2&&(din[3:2]==2'b11) || shl2&&(din[3:2]==2'b11) || mvr1&&(din[3:2]==2'b11) || mvrd2&&(din[3:2]==2'b11) || lad5&&(din[3:2]==2'b11);
assign r3bus= add1&&(din[1:0]==2'b11) || add2&&(din[3:2]==2'b11) || sub1&&(din[1:0]==2'b11) || sub2&&(din[3:2]==2'b11) || and1&&(din[1:0]==2'b11) || and2&&(din[3:2]==2'b11) || or1&&(din[1:0]==2'b11) || or2&&(din[3:2]==2'b11) || inc1&&(din[3:2]==2'b11) || dec1&&(din[3:2]==2'b11) || not1&&(din[3:2]==2'b11) || shl1&&(din[3:2]==2'b11) || mvr1&&(din[1:0]==2'b11) || sto4&&(din[3:2]==2'b11);
assign xload= add1 || sub1 || and1 || or1;
//assign alus= (3'b000&&add2) || (3'b001&&sub2) || (3'b010&&and2) || (3'b011&&or2) || (3'b100&&inc1) || (3'b101&&dec1) || (3'b110&&not1) || (3'b111&&shl1);
assign yload= add2 || sub2 || and2 || or2 || inc1 || dec1 || not1 || shl1;
assign ybus= add3 || sub3 || and3 || or3 || inc2 || dec2 || not2 || shl2;
assign zload= add2 || sub2 || and2 || or2 || inc1 || dec1 || not1 || shl1;
assign read= fetch2 || mvrd1 || jmp1 || jmp2 || jmpz1 || jmpz2 || jpnz1 || jpnz2 || lad1 || lad2 || lad4 || sto1 || sto2;
assign write= sto5;
assign membus= fetch2 || mvrd1 || jmp1 || jmp2 || jmpz1 || jmpz2 || jpnz1 || jpnz2 || lad1 || lad2 || lad4 || sto1 || sto2;
assign busmem= sto5;


always@(posedge clk or negedge reset)
begin
if(!reset)
	begin//各指令清零，以下已为nop指令清零，请补充其他指令，为其他指令清零
		inop<=0;	iadd<=0;	isub<=0; iand<=0;
		ior<=0;	iinc<=0;	idec<=0;	inot<=0;
		ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
		ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
	end
else 
	begin
		case(din[7:4])	//译码处理过程
		4'd0:
			begin		//op为0000，是nop指令，因此这里inop的值是1，而其他指令应该清零，请补充为其他指令清零的语句
			inop<=1;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd1:
			begin
				//op为0001，应该是add指令，因此iadd指令为1，其他指令都应该是0。
				//后续各分支类似，只有一条指令为1，其他指令为0，以下分支都给出nop指令的赋值，需要补充其他指令
			alus=3'b000;	
			inop<=0;	iadd<=1;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd2:
			begin
			alus=3'b001;
			inop<=0;	iadd<=0;	isub<=1; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd3:
			begin
			alus=3'b010;
			inop<=0;	iadd<=0;	isub<=0; iand<=1;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd4:
			begin
			alus=3'b011;
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=1;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd5:
			begin
			alus=3'b100;
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=1;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd6:
			begin
			alus=3'b101;
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=1;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd7:
			begin
			alus=3'b110;
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=1;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
			//如果还有分支，可以继续写，如果没有分支了，写上defuault语句
		4'd8:	
			begin
			alus=3'b111;
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=1;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd9:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=1;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd10:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=1;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd11:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=1;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd12:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=1;ijpnz<=0;ilad<=0;	isto<=0;
			end
		4'd13:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=1;ilad<=0;	isto<=0;
			end
		4'd14:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=1;	isto<=0;
			end
		4'd15:	
			begin
			inop<=0;	iadd<=0;	isub<=0; iand<=0;
			ior<=0;	iinc<=0;	idec<=0;	inot<=0;
			ishl<=0;	imvr<=0;	imvrd<=0;ijmp<=0;
			ijmpz<=0;ijpnz<=0;ilad<=0;	isto<=1;
			end
		endcase
	end
end

always @(posedge clk or negedge reset)
begin
if(!reset) //reset清零
begin
	t0<=1;
	t1<=0;
	t2<=0;
	t3<=0;
	t4<=0;
	t5<=0;
	t6<=0;
	t7<=0;
end
else
begin
	if(inc) //运行
	begin
	t7<=t6;
	t6<=t5;
	t5<=t4;
	t4<=t3;
	t3<=t2;
	t2<=t1;
	t1<=t0;
	t0<=0;
	end
	else if(clr) //清零
	begin
	t0<=1;
	t1<=0;
	t2<=0;
	t3<=0;
	t4<=0;
	t5<=0;
	t6<=0;
	t7<=0;
	end
end

end
/*—————结束—————*/
endmodule
	
		